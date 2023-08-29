package org.kurron.gurps.character

import com.fasterxml.jackson.databind.ObjectMapper
import org.kurron.gurps.shared.MessageStructure
import java.util.UUID
import org.kurron.gurps.shared.SharedConfiguration.Companion.CHARACTER_COMMAND_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_EXCHANGE
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CharacterResource(private val jackson: ObjectMapper) {

    @PostMapping(path = ["/character"])
    @ResponseStatus(HttpStatus.OK)
    fun handleInitialize(): InitializeCharacterResponse {
        val command = InitializeCharacterCommand(payload = "${UUID.randomUUID()}")
        val responseType = object : ParameterizedTypeReference<InitializeCharacterResponse>() {}
        val response = InitializeCharacterResponse.randomInstance()
        println("response = $response")
        return response
    }
}