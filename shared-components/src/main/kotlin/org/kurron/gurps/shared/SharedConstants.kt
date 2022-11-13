package org.kurron.gurps.shared

/**
 * This file houses the values that have to be synced between modules in order for the system to operate correctly.
 */
class SharedConstants {
    companion object {
        const val COMMAND_ROUTING_KEY = "gurps-commands"
        const val COMMAND_QUEUE_NAME = "gurps-commands"
        const val COMMAND_EXCHANGE_NAME = "gurps-commands"
        const val EVENT_ROUTING_KEY = "ignored-by-fanout-exchanges"
        const val EVENT_QUEUE_NAME = "gurps-events"
        const val EVENT_EXCHANGE_NAME = "gurps-events"
        const val MESSAGE_ROUTING_LABEL = "gurps-routing-label"
    }
}