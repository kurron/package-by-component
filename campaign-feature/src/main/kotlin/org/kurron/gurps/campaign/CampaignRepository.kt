package org.kurron.gurps.campaign

import org.springframework.data.repository.ListCrudRepository

internal interface CampaignRepository: ListCrudRepository<Campaign, Int>