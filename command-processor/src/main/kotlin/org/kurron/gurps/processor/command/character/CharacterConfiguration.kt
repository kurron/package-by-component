package org.kurron.gurps.processor.command.character

import org.kurron.gurps.shared.CharacterReply
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.Splitter
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel

@Configuration
class CharacterConfiguration {
    @Bean
    fun characterCommands(): MessageChannel = DirectChannel()

    @Bean
    fun characterCommandProcessor(): CharacterCommandProcessor = CharacterCommandProcessor()

    @Splitter(inputChannel = "toSplitMessagesChannel", outputChannel = "routeSplitMessagesChannel")
    fun characterSplitter(toSplit: CharacterReply): List<Any> {
        return listOf(toSplit.character,toSplit.event)
    }
}