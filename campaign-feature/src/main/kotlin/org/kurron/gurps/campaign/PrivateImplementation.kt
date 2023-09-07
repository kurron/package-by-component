package org.kurron.gurps.campaign

import org.springframework.stereotype.Component

@Component("Campaign")
internal class PrivateImplementation(private val repository: CampaignRepository): ProvidedInterface {
    override fun foo(): String {
        val data = Campaign.randomInstance()
        val primaryKey = repository.save(data)
        return "org.kurron.gurps.campaign.PrivateImplementation persisted $primaryKey"
    }
}