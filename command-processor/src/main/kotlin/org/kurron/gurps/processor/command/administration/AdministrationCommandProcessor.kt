package org.kurron.gurps.processor.command.administration

import org.kurron.gurps.shared.Administration
import org.kurron.gurps.shared.AdministrationReply
import org.kurron.gurps.shared.Command
import org.kurron.gurps.shared.Event
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Headers

class AdministrationCommandProcessor {
    @ServiceActivator(inputChannel = "administrationCommands", outputChannel = "toSplitMessagesChannel", requiresReply = "true")
    fun handleAdministrationCommandsMessage(command: Command, @Headers headers: Map<String,Any>) : AdministrationReply {
        // Spring transforms the AMQP message into a Command
        // process the command by writing to MongoDB
        // create an event and return it, where it will be picked up by the AMQP outbound adapter
        println("handleAdministrationCommandsMessage: $command")
        return AdministrationReply(Administration(command.label, "http://gurps.example.com/administration/1"), Event("event.administration"))
    }
}