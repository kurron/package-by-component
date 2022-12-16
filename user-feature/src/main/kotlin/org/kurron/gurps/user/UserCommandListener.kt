package org.kurron.gurps.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.kurron.gurps.shared.SharedConfiguration
import org.kurron.gurps.shared.SharedConfiguration.Companion.PRIMARY_KEY
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitOperations

class UserCommandListener(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper): MessageListener {
    override fun onMessage(message: Message) {
        val assetID: String = message.messageProperties.getHeader(PRIMARY_KEY)
        val response = CreateUserResponse(payload = CreateUserResponse.Payload(assetID))
        val responseMessage = response.toMessage(jackson, message.messageProperties.correlationId)
        val event = UserCreatedEvent(payload = UserCreatedEvent.Payload(assetID))
        val eventMessage = event.toMessage(jackson, message.messageProperties.correlationId)
        rabbitmq.send(message.messageProperties.replyTo, responseMessage)
        rabbitmq.send(SharedConfiguration.EVENT_EXCHANGE, SharedConfiguration.USER_EVENT_KEY, eventMessage)
    }
}