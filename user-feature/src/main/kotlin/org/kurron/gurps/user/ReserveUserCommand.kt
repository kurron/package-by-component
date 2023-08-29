package org.kurron.gurps.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import java.util.Date
import java.util.UUID
import org.kurron.gurps.shared.MessageStructure
import org.kurron.gurps.shared.SharedConfiguration
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_LABEL_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.CREATE_USER_LABEL
import org.springframework.http.MediaType

data class ReserveUserCommand(@JsonProperty("payload") val payload: Payload,
                              @JsonProperty("label") val label: String = CREATE_USER_LABEL,
                              @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "command", feature = "user"),
                              @JsonProperty("id") val id: UUID = UUID.randomUUID()) {
    fun toMessage(jackson: ObjectMapper): String {
        val bytes = jackson.writeValueAsBytes(this)
        val now = Date.from(Instant.now())
        val type = "${structure.type}/${structure.feature};version=${structure.version}"
        return "FOO BAR!"
    }

    data class Payload(@JsonProperty("username") val username: String)
}
