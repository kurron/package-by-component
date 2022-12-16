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
            CREATE_USER_LABEL -> processCreateUser(message)
            else -> println("$label not supported!")
        }
    }

    private fun processCreateUser(message: Message) {
        val type = object : TypeReference<CreateUserCommand>() {}
        val command = jackson.readValue(message.body, type)
        // TODO: do some database work
        val response = CreateUserResponse(payload = CreateUserResponse.Payload(username = command.payload.username, password = "some password"))
        val responseMessage = response.toMessage(jackson, message.messageProperties.correlationId)
        val event = UserCreatedEvent(payload = UserCreatedEvent.Payload(command.payload.username))
        val eventMessage = event.toMessage(jackson, message.messageProperties.correlationId)
        rabbitmq.send(message.messageProperties.replyTo, responseMessage)
        // TODO: outbox pattern
        rabbitmq.send(SharedConfiguration.EVENT_EXCHANGE, SharedConfiguration.USER_EVENT_KEY, eventMessage)
    }
}

/*
token - claim-_mNEcN4mFmxHWgyqFvZd
port = 32400
host network - true
data - /mnt/secondary/nidavellir/low-priority/videos
config - /mnt/secondary/applications/plex
/raw-blu-rays - /mnt/secondary/nidavellir/low-priority/raw-video (read-only)
intel GPU
 */