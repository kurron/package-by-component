package org.kurron.gurps.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.kurron.gurps.shared.MessageStructure
import org.kurron.gurps.shared.SharedConfiguration
import java.time.Instant
import java.util.Date

data class ReserveUserResponse(@JsonProperty("payload") val payload: Payload,
                               @JsonProperty("label") val label: String = "command.user.reserve-user",
                               @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "response", feature = "user"),
                               @JsonProperty("id") val id: Int = 0) {
    fun toMessage(jackson: ObjectMapper, correlationId: String): String  {
        val bytes = jackson.writeValueAsBytes(this)
        val now = Date.from(Instant.now())
        val type = "${structure.type}/${structure.feature}/${structure.version}"
        return "FOO BAR!"
    }

    data class Payload(@JsonProperty("username") val username: String, @JsonProperty("password") val password: String)
}