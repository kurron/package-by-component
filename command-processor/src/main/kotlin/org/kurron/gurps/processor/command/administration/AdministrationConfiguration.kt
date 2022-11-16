package org.kurron.gurps.processor.command.administration

import org.kurron.gurps.shared.AdministrationReply
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.Splitter
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel

@Configuration
class AdministrationConfiguration {
    @Bean
    fun administrationCommands(): MessageChannel = DirectChannel()

    @Bean
    fun administrationCommandProcessor(): AdministrationCommandProcessor = AdministrationCommandProcessor()

    @Splitter(inputChannel = "toSplitMessagesChannel", outputChannel = "routeSplitMessagesChannel")
    fun administrationSplitter(toSplit: AdministrationReply): List<Any> {
        return listOf(toSplit.administration,toSplit.event)
    }

}