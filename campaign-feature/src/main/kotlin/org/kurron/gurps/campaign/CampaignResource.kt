package org.kurron.gurps.campaign

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import org.kurron.gurps.shared.SharedConfiguration.Companion.CAMPAIGN_COMMAND_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_EXCHANGE
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CampaignResource(private val jackson: ObjectMapper) {



    @PostMapping(path = ["/campaign"])
    @ResponseStatus(HttpStatus.OK)
    fun handleInitialize(): InitializeCampaignResponse {
        val command = InitializeCampaignCommand(payload = "${UUID.randomUUID()}")
        val responseType = object : ParameterizedTypeReference<InitializeCampaignResponse>() {}
        val response = InitializeCampaignResponse.randomInstance()
        println("response = $response")
        return response
    }
}