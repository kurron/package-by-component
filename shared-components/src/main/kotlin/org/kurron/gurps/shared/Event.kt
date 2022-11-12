package org.kurron.gurps.shared

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents a fact: something that has occurred in the past, e.g. "Campaign 1234 has been deleted from account 456".
 */
data class Event(@JsonProperty("foo") val foo: String)
