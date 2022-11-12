package org.kurron.gurps.processor.cli

import org.kurron.gurps.shared.Command
import org.kurron.gurps.shared.SharedConstants
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.shell.Availability
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellMethodAvailability
import java.util.concurrent.ThreadLocalRandom

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
    fun tickle() {
        val command = Command(foo = ThreadLocalRandom.current().nextLong().toString(16))
        amqp.convertAndSend(SharedConstants.COMMAND_EXCHANGE_NAME, SharedConstants.COMMAND_ROUTING_KEY, command)
    }
}