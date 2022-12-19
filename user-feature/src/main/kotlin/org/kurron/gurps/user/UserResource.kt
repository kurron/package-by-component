package org.kurron.gurps.user

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_EXCHANGE
import org.kurron.gurps.shared.SharedConfiguration.Companion.USER_COMMAND_KEY
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.mediatype.hal.HalModelBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
class UserResource(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper) {

    // POST /asset - create
    // PUT /asset/{id} - update
    // GET /asset/{id} - read
    // DELETE /asset/{id} - delete
    // bulk upload?
    @PostMapping(path = ["/user"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun handleInitialize(builder: UriComponentsBuilder, @RequestBody body: ReserveUserCommand.Payload): ResponseEntity<RepresentationModel<out RepresentationModel<*>>> {
        val id = UUID.randomUUID().toString()
        val command = ReserveUserCommand(payload = body)
        val message = command.toMessage(jackson)
        val correlationData = CorrelationData(command.id.toString())
        val responseType = object : ParameterizedTypeReference<ReserveUserResponse>() {}
        val response = rabbitmq.convertSendAndReceiveAsType(COMMAND_EXCHANGE, USER_COMMAND_KEY, message, correlationData, responseType)!!
        val self = WebMvcLinkBuilder.linkTo(UserResource::class.java).slash(id).withSelfRel()
        val hal = HalModelBuilder.halModelOf(response.payload).link(self).build()
        return ResponseEntity.ok(hal)
    }
}