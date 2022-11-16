package org.kurron.gurps.processor.command.campaign

import org.kurron.gurps.shared.Campaign
import org.kurron.gurps.shared.CampaignReply
import org.kurron.gurps.shared.Command
import org.kurron.gurps.shared.Event
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Headers

class CampaignCommandProcessor {
    @ServiceActivator(inputChannel = "campaignCommands", outputChannel = "toSplitMessagesChannel", requiresReply = "true")
    fun handleCampaignMessage(command: Command, @Headers headers: Map<String, Any>): CampaignReply {
        // Spring transforms the AMQP message into a Command
        // process the command by writing to MongoDB
        // create an event and return it, where it will be picked up by the AMQP outbound adapter
        println("handleCampaignMessage: $command")
        val campaign = Campaign(command.label, "http://gurps.example.com/campaign/1")
        val event = Event("event.campaign")
        return CampaignReply(campaign,event)
    }
}