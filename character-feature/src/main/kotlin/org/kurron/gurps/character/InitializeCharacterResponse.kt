package org.kurron.gurps.character

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import java.util.Date
import java.util.UUID
import org.kurron.gurps.shared.MessageStructure
import org.kurron.gurps.shared.SharedConfiguration

data class InitializeCharacterResponse(@JsonProperty("payload") val payload: String,
                                       @JsonProperty("label") val label: String = "command.character.initialize-character",
                                       @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "response", feature = "character"),
                                       @JsonProperty("id") val id: UUID = UUID.randomUUID()) {
    companion object {
        fun randomInstance() : InitializeCharacterResponse = InitializeCharacterResponse(payload = "bar")
    }

}