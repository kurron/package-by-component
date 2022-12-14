package org.kurron.gurps.asset

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import java.util.Date
import java.util.UUID
import org.kurron.gurps.shared.MessageStructure
import org.kurron.gurps.shared.SharedConfiguration
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.core.MessagePropertiesBuilder

data class AssetInitializedEvent(@JsonProperty("payload") val payload: Payload,
                                 @JsonProperty("label") val label: String = "event.asset.asset-initialized",
                                 @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "event", feature = "asset"),
                                 @JsonProperty("id") val id: UUID = UUID.randomUUID()) {
    fun toMessage(jackson: ObjectMapper, correlationId: String): Message {
        val bytes = jackson.writeValueAsBytes(this)
        val now = Date.from(Instant.now())
        val type = "${structure.type}/${structure.feature};version=${structure.version}"
        val properties = MessagePropertiesBuilder.newInstance()
                                                 .setAppId(SharedConfiguration.APPLICATION_ID)
                                                 .setMessageId(id.toString())
                                                 .setTimestamp(now)
                                                 .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                                                 .setType(type)
                                                 .setCorrelationId(correlationId)
                                                 .build()
        return MessageBuilder.withBody(bytes).andProperties(properties).build()
    }

    data class Payload(@JsonProperty("id") val id: String)
}
