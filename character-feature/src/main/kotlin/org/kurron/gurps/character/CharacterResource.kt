package org.kurron.gurps.character

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import org.kurron.gurps.shared.SharedConfiguration.Companion.CHARACTER_COMMAND_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_EXCHANGE
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CharacterResource(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper) {

    @PostMapping(path = ["/character"])
    @ResponseStatus(HttpStatus.OK)
    fun handleInitialize(): InitializeCharacterResponse {
        val command = InitializeCharacterCommand(payload = "${UUID.randomUUID()}")
        val message = command.toMessage(jackson)
        val correlationData = CorrelationData(command.id.toString())
        val responseType = object : ParameterizedTypeReference<InitializeCharacterResponse>() {}
        val response = rabbitmq.convertSendAndReceiveAsType(COMMAND_EXCHANGE, CHARACTER_COMMAND_KEY, message, correlationData, responseType)!!
        println("response = $response")
        return response
    }
}