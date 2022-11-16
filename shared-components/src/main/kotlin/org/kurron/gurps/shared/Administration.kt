package org.kurron.gurps.shared

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A reply to an administration command.
 */
data class Administration(@JsonProperty("label") val label: String, @JsonProperty("id") val id: String, @JsonProperty("foo") val foo: List<String> = listOf())
