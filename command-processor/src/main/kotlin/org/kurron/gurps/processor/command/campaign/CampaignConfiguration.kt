package org.kurron.gurps.processor.command.campaign

import org.kurron.gurps.shared.CampaignReply
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.Splitter
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel

@Configuration
class CampaignConfiguration {
    @Bean
    fun campaignCommands(): MessageChannel = DirectChannel()

    @Bean
    fun campaignCommandProcessor(): CampaignCommandProcessor = CampaignCommandProcessor()

    @Splitter(inputChannel = "toSplitMessagesChannel", outputChannel = "routeSplitMessagesChannel")
    fun campaignSplitter(toSplit: CampaignReply): List<Any> {
        return listOf(toSplit.campaign,toSplit.event)
    }
}