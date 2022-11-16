package org.kurron.gurps.shared

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A reply to a campaign command.
 */
data class Campaign(@JsonProperty("label") val label: String, @JsonProperty("id") val id: String, @JsonProperty("foo") val foo: List<String> = listOf())
