package org.kurron.gurps.processor.cli

import org.kurron.gurps.shared.Command
import org.kurron.gurps.shared.SharedConstants.Companion.COMMAND_EXCHANGE_NAME
import org.kurron.gurps.shared.SharedConstants.Companion.COMMAND_ROUTING_KEY
import org.kurron.gurps.shared.SharedConstants.Companion.MESSAGE_ROUTING_LABEL
import org.springframework.amqp.core.*
import org.springframework.shell.Availability
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellMethodAvailability
import org.springframework.shell.standard.ShellOption
import java.util.UUID

@ShellComponent
@ShellCommandGroup("work in progress")
class ExampleCommands(private val amqp: AmqpTemplate) {
    @ShellMethod(value = "Add two numbers together", key = ["add"])
    fun add(a: Int, b: Int): Int = a+b

    private var connected = true

    @ShellMethod(value = "Connect to GURPs server", key = ["connect"])
    fun connect() { connected = true }

    @ShellMethod(value = "Disconnect from GURPs server", key = ["disconnect"])
    fun disconnect() { connected = false }

    @ShellMethodAvailability("connect", "disconnect")
    fun connectedCheck(): Availability? {
        return if (connected) Availability.available() else Availability.unavailable("You are not connected")
    }

    @ShellMethod(value = "Send a test command", key = ["tickle"])
    fun tickle(@ShellOption(help = "campaign, character or administration", defaultValue = "campaign") type: String) {
        val messagePostProcessor = object : MessagePostProcessor {
            override fun postProcessMessage(message: Message): Message {
                message.messageProperties.setHeader(MESSAGE_ROUTING_LABEL, "command.$type.new")
                message.messageProperties.correlationId = UUID.randomUUID().toString()
                message.messageProperties.deliveryMode = MessageDeliveryMode.NON_PERSISTENT
                message.messageProperties.appId = "GURPS CLI"
                message.messageProperties.type = "command.$type.new"
                // message.messageProperties.userId = "Gary"
                return message
            }
        }
        val command = Command(label = "command.$type.new")
        val response = amqp.convertSendAndReceive(COMMAND_EXCHANGE_NAME, COMMAND_ROUTING_KEY, command, messagePostProcessor)
        println(response)
    }

}