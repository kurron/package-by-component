package org.kurron.gurps.shared.asset

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
import org.springframework.http.MediaType

data class InitializeAssetCommand(@JsonProperty("payload") val payload: String,
                                  @JsonProperty("label") val label: String = "command.asset.initialize-asset",
                                  @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "command", feature = "asset"),
                                  @JsonProperty("id") val id: UUID = UUID.randomUUID()) {
    companion object {
        fun randomInstance() : InitializeAssetCommand = InitializeAssetCommand(payload = "foo")
    }

    fun toMessage(jackson: ObjectMapper): Message {
        val bytes = jackson.writeValueAsBytes(this)
        val now = Date.from(Instant.now())
        val type = "${structure.type}/${structure.feature};version=${structure.version}"
        val properties = MessagePropertiesBuilder.newInstance()
                                                 .setAppId(SharedConfiguration.APPLICATION_ID)
                                                 .setMessageId(id.toString())
                                                 .setHeader("foo","bar")
                                                 .setTimestamp(now)
                                                 .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                                                 .setType(type)
                                                 .setContentType(MediaType.APPLICATION_JSON_VALUE)
                                                 .setCorrelationId(id.toString())
                                                 .build()
        return MessageBuilder.withBody(bytes).andProperties(properties).build()
    }
}
