package org.kurron.gurps.user

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.kurron.gurps.shared.SharedConfiguration
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_LABEL_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.CREATE_USER_LABEL
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitOperations

class UserCommandListener(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper): MessageListener {
    override fun onMessage(message: Message) {
        when(val label = message.messageProperties.headers[COMMAND_LABEL_KEY]) {
            CREATE_USER_LABEL -> processReserveUser(message)
            else -> println("$label not supported!")
        }
    }

    private fun processReserveUser(message: Message) {
        val type = object : TypeReference<ReserveUserCommand>() {}
        val command = jackson.readValue(message.body, type)
        // TODO: do some database work
        val response = ReserveUserResponse(payload = ReserveUserResponse.Payload(username = command.payload.username, password = "some password"))
        val responseMessage = response.toMessage(jackson, message.messageProperties.correlationId)
        val event = UserReservedEvent(payload = UserReservedEvent.Payload(command.payload.username))
        val eventMessage = event.toMessage(jackson, message.messageProperties.correlationId)
        rabbitmq.send(message.messageProperties.replyTo, responseMessage)
        // TODO: outbox pattern
        rabbitmq.send(SharedConfiguration.EVENT_EXCHANGE, SharedConfiguration.USER_EVENT_KEY, eventMessage)
    }
}
