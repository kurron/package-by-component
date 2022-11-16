package org.kurron.gurps.processor.command.character

import org.kurron.gurps.shared.Character
import org.kurron.gurps.shared.CharacterReply
import org.kurron.gurps.shared.Command
import org.kurron.gurps.shared.Event
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Headers

class CharacterCommandProcessor {
    @ServiceActivator(inputChannel = "characterCommands", outputChannel = "toSplitMessagesChannel", requiresReply = "true")
    fun handleCharacterMessage(command: Command, @Headers headers: Map<String,Any>) : CharacterReply {
        // Spring transforms the AMQP message into a Command
        // process the command by writing to MongoDB
        // create an event and return it, where it will be picked up by the AMQP outbound adapter
        println("handleCharacterMessage: $command")
        return CharacterReply(Character(command.label, "http://gurps.example.com/character/1"), Event("event.character"))
    }
}