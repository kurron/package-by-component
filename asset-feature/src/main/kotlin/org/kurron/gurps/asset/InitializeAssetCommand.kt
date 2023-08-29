package org.kurron.gurps.asset

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import java.util.Date
import java.util.UUID
import org.kurron.gurps.shared.MessageStructure
import org.kurron.gurps.shared.SharedConfiguration
import org.kurron.gurps.shared.SharedConfiguration.Companion.PRIMARY_KEY
import org.springframework.http.MediaType

data class InitializeAssetCommand(@JsonProperty("payload") val payload: Payload,
                                  @JsonProperty("label") val label: String = "command.asset.initialize-asset",
                                  @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "command", feature = "asset"),
                                  @JsonProperty("id") val id: UUID = UUID.randomUUID()) {
    data class Payload(@JsonProperty("name") val name: String)
}
