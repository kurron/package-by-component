package org.kurron.gurps.shared

/**
 * Internal structure intended to be split into separate messages and routed separately
 */
data class CharacterReply(val character: Character, val event: Event)
