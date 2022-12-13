package org.kurron.gurps.campaign

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import org.kurron.gurps.shared.SharedConfiguration
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitOperations

class CampaignCommandListener(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper): MessageListener {
    override fun onMessage(message: Message) {
        val response = InitializeCampaignResponse(payload = "${UUID.randomUUID()}")
        val responseMessage = response.toMessage(jackson, message.messageProperties.correlationId)
        val event = CampaignInitializedEvent(payload = response.payload)
        val eventMessage = event.toMessage(jackson, message.messageProperties.correlationId)
        rabbitmq.send(message.messageProperties.replyTo, responseMessage)
        rabbitmq.send(SharedConfiguration.EVENT_EXCHANGE, SharedConfiguration.CAMPAIGN_EVENT_KEY, eventMessage)
    }
}