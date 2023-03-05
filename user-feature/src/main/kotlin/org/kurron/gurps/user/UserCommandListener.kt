package org.kurron.gurps.user

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.exception.ExceptionUtils
import org.kurron.gurps.shared.FailureDetails
import org.kurron.gurps.shared.FailureType
import org.kurron.gurps.shared.SharedConfiguration
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_LABEL_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.CREATE_USER_LABEL
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.dao.OptimisticLockingFailureException
import java.net.URI

class UserCommandListener(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper, private val repository: AccountRepository): MessageListener {
    override fun onMessage(message: Message) {
        try {
            when(val label = message.messageProperties.headers[COMMAND_LABEL_KEY]) {
                CREATE_USER_LABEL -> processReserveUser(message)
                else -> println("$label not supported!")
            }
        } catch (e: Exception) {
            val rootCause = ExceptionUtils.getRootCause(e)
            println("Failure: ${rootCause.message}")
        }
    }

    private fun processReserveUser(message: Message) {
        message.messageProperties.type
        val type = object : TypeReference<ReserveUserCommand>() {}
        val command = jackson.readValue(message.body, type)
        val dto = Account(username = command.payload.username, password = "password")
        val responseMessage = try {
            val entity = repository.save(dto)
            val response = ReserveUserResponse(id = entity.id, payload = ReserveUserResponse.Payload(username = entity.username, password = entity.password))
            response.toMessage(jackson, message.messageProperties.correlationId)
        }
        catch (e: OptimisticLockingFailureException) {
            val response = FailureDetails("Unable to acquire a database lock. The row may no longer be exist.", URI.create("about:blank"), FailureType.OPTIMISTIC_LOCKING_FAILURE)
            response.toMessage(jackson, message.messageProperties.correlationId)
        }
        catch (e: IllegalArgumentException) {
            val response = FailureDetails("JPA call did not provide an entity on the save call.", URI.create("about:blank"), FailureType.NULL_JPA_ENTITY)
            response.toMessage(jackson, message.messageProperties.correlationId)
        }
        rabbitmq.send(message.messageProperties.replyTo, responseMessage)

        // TODO: use outbox pattern
        val event = UserReservedEvent(payload = UserReservedEvent.Payload(command.payload.username))
        val eventMessage = event.toMessage(jackson, message.messageProperties.correlationId)
        rabbitmq.send(SharedConfiguration.EVENT_EXCHANGE, SharedConfiguration.USER_EVENT_KEY, eventMessage)
    }
}
