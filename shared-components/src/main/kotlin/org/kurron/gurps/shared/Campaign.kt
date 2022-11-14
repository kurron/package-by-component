package org.kurron.gurps.shared

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A request to make something happen, usually a change to the system state, e.g. "Delete Campaign 1234 from my account".
 */
data class Campaign(@JsonProperty("label") val label: String, @JsonProperty("id") val id: String, @JsonProperty("foo") val foo: List<String> = listOf())
