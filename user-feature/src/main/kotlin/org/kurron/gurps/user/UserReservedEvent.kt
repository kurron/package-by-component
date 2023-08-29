package org.kurron.gurps.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import java.util.Date
import java.util.UUID
import org.kurron.gurps.shared.MessageStructure
import org.kurron.gurps.shared.SharedConfiguration

data class UserReservedEvent(@JsonProperty("payload") val payload: Payload,
                             @JsonProperty("label") val label: String = "event.user.user-reserved",
                             @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "event", feature = "user"),
                             @JsonProperty("id") val id: UUID = UUID.randomUUID()) {
    fun toMessage(jackson: ObjectMapper, correlationId: String): String {
        val bytes = jackson.writeValueAsBytes(this)
        val now = Date.from(Instant.now())
        val type = "${structure.type}/${structure.feature};version=${structure.version}"
        return "FOO BAR!"
    }

    data class Payload(@JsonProperty("id") val id: String)
}
