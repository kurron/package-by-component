package org.kurron.gurps.processor.command

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A request to make something happen, usually a change to the system state, e.g. "Delete Campaign 1234 from my account".
 */
data class Command(@JsonProperty("foo") val foo: String)
