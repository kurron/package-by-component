package org.kurron.gurps.character

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

data class InitializeCharacterResponse(@JsonProperty("payload") val payload: String,
                                       @JsonProperty("label") val label: String = "command.character.initialize-character",
                                       @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "response", feature = "character"),
                                       @JsonProperty("id") val id: UUID = UUID.randomUUID()) {
    companion object {
        fun randomInstance() : InitializeCharacterResponse = InitializeCharacterResponse(payload = "bar")
    }

    fun toMessage(jackson: ObjectMapper, correlationId: String): Message  {
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
                                                 .setCorrelationId(correlationId)
                                                 .build()
        return MessageBuilder.withBody(bytes).andProperties(properties).build()
    }
}