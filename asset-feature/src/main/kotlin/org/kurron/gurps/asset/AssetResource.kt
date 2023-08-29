package org.kurron.gurps.asset

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import org.kurron.gurps.shared.SharedConfiguration.Companion.ASSET_COMMAND_KEY
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_EXCHANGE
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
class AssetResource(private val jackson: ObjectMapper) {

    // POST /asset - create
    // PUT /asset/{id} - update
    // GET /asset/{id} - read
    // DELETE /asset/{id} - delete
    // bulk upload?
    @PostMapping(path = ["/asset"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun handleInitialize(builder: UriComponentsBuilder, @RequestBody body: InitializeAssetCommand.Payload): InitializeAssetResponse.Payload {
        val assetID = builder.path("/asset/{id}").buildAndExpand(UUID.randomUUID().toString()).toUriString()
        val command = InitializeAssetCommand(payload = body)
        val responseType = object : ParameterizedTypeReference<InitializeAssetResponse>() {}
        val response = InitializeAssetResponse(payload = InitializeAssetResponse.Payload("foo"))
        println("response = $response")
        return response.payload
    }
}