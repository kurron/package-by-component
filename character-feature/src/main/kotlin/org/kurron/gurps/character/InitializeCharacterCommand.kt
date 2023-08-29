package org.kurron.gurps.character

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import java.util.Date
import java.util.UUID
import org.kurron.gurps.shared.MessageStructure
import org.kurron.gurps.shared.SharedConfiguration
import org.springframework.http.MediaType

data class InitializeCharacterCommand(@JsonProperty("payload") val payload: String,
                                      @JsonProperty("label") val label: String = "command.character.initialize-character",
                                      @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "command", feature = "character"),
                                      @JsonProperty("id") val id: UUID = UUID.randomUUID()) {
    companion object {
        fun randomInstance() : InitializeCharacterCommand = InitializeCharacterCommand(payload = "foo")
    }
}
