package org.kurron.gurps.shared

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Properties used to describe the structure of the message.
 * @property version semantic version of the structure, e.g. 1.2.3.
 * @property type what type of message this is, e.g. command or event.
 * @property feature what feature set this message belongs, e.g. character.
 */
data class MessageStructure(@JsonProperty("version") val version: String,
                            @JsonProperty("type") val type: String,
                            @JsonProperty("feature") val feature: String)
