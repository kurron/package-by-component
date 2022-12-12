package org.kurron.gurps.asset.inbound

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import org.kurron.gurps.shared.SharedConfiguration.Companion.ASSET_COMMAND_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_EXCHANGE
import org.kurron.gurps.shared.asset.InitializeAssetCommand
import org.kurron.gurps.shared.asset.InitializeAssetResponse
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AssetResource(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper) {



    @PostMapping(path = ["/asset"])
    @ResponseStatus(HttpStatus.OK)
    fun handleInitialize(): InitializeAssetResponse {
        val command = InitializeAssetCommand(payload = "${UUID.randomUUID()}")
        val message = command.toMessage(jackson)
        val correlationData = CorrelationData(command.id.toString())
        val responseType = object : ParameterizedTypeReference<InitializeAssetResponse>() {}
        val response = rabbitmq.convertSendAndReceiveAsType(COMMAND_EXCHANGE, ASSET_COMMAND_KEY, message, correlationData, responseType)!!
        println("response = $response")
        return response
    }
}