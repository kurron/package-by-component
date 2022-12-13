package org.kurron.gurps.campaign

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import org.kurron.gurps.shared.SharedConfiguration.Companion.CAMPAIGN_COMMAND_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_EXCHANGE
import org.kurron.gurps.shared.campaign.InitializeCampaignCommand
import org.kurron.gurps.shared.campaign.InitializeCampaignResponse
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CampaignResource(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper) {



    @PostMapping(path = ["/campaign"])
    @ResponseStatus(HttpStatus.OK)
    fun handleInitialize(): InitializeCampaignResponse {
        val command = InitializeCampaignCommand(payload = "${UUID.randomUUID()}")
        val message = command.toMessage(jackson)
        val correlationData = CorrelationData(command.id.toString())
        val responseType = object : ParameterizedTypeReference<InitializeCampaignResponse>() {}
        val response = rabbitmq.convertSendAndReceiveAsType(COMMAND_EXCHANGE, CAMPAIGN_COMMAND_KEY, message, correlationData, responseType)!!
        println("response = $response")
        return response
    }
}