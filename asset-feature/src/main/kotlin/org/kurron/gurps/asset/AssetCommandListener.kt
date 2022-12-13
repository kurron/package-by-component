package org.kurron.gurps.asset

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import org.kurron.gurps.shared.SharedConfiguration
import org.kurron.gurps.shared.asset.AssetInitializedEvent
import org.kurron.gurps.shared.asset.InitializeAssetResponse
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitOperations

class AssetCommandListener(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper): MessageListener {
    override fun onMessage(message: Message) {
        val response = InitializeAssetResponse(payload = "${UUID.randomUUID()}")
        val responseMessage = response.toMessage(jackson, message.messageProperties.correlationId)
        val event = AssetInitializedEvent(payload = response.payload)
        val eventMessage = event.toMessage(jackson, message.messageProperties.correlationId)
        rabbitmq.send(message.messageProperties.replyTo, responseMessage)
        rabbitmq.send(SharedConfiguration.EVENT_EXCHANGE, SharedConfiguration.ASSET_EVENT_KEY, eventMessage)
    }
}