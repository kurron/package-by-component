package org.kurron.gurps.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.kurron.gurps.shared.MessageStructure
import org.kurron.gurps.shared.SharedConfiguration
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.core.MessagePropertiesBuilder
import java.time.Instant
import java.util.Date

data class ReserveUserResponse(@JsonProperty("payload") val payload: Payload,
                               @JsonProperty("label") val label: String = "command.user.reserve-user",
                               @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "response", feature = "user"),
                               @JsonProperty("id") val id: Int = 0) {
    fun toMessage(jackson: ObjectMapper, correlationId: String): Message  {
        val bytes = jackson.writeValueAsBytes(this)
        val now = Date.from(Instant.now())
        val type = "${structure.type}/${structure.feature}/${structure.version}"
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

    data class Payload(@JsonProperty("username") val username: String, @JsonProperty("password") val password: String)
}