package org.kurron.gurps.user

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.kurron.gurps.shared.FailureDetails
import org.kurron.gurps.shared.SharedConfiguration.Companion.COMMAND_EXCHANGE
import org.kurron.gurps.shared.SharedConfiguration.Companion.USER_COMMAND_KEY
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.mediatype.hal.HalModelBuilder
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(path = ["/user"])
class UserResource(private val rabbitmq: RabbitOperations, private val jackson: ObjectMapper) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaTypes.HAL_JSON_VALUE])
    fun create(builder: UriComponentsBuilder, @RequestBody body: ReserveUserCommand.Payload): ResponseEntity<*> {
        return try {
            val command = ReserveUserCommand(payload = body)
            val message = command.toMessage(jackson)
            val correlationData = CorrelationData(command.id.toString())
            //TODO: we CANNOT assume the response type. It is dynamic depending on error conditions!
            val responseType = object : ParameterizedTypeReference<Message>() {}
            val response = rabbitmq.convertSendAndReceiveAsType(COMMAND_EXCHANGE, USER_COMMAND_KEY, message, correlationData, responseType)
            if (null == response) {
                // always the same -- we can't know the details
                val problem = Problem.create()
                                     .withType(builder.replacePath("/problems/service-timeout").build().toUri())
                                     .withTitle("service timed out")
                                     .withDetail("service never completed")
                                     .withInstance(builder.replacePath("/logs/{correlation}").buildAndExpand(command.id).toUri())
                                     .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem)
            }
            else if (response.messageProperties.type.equals("failure/all/1.0.0")) {
                val type = object : TypeReference<FailureDetails>() {}
                val failure = jackson.readValue(response.body, type)
                val problem = failure.toProblem(builder)
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem)
            }
            else if (response.messageProperties.type.equals("response/user/1.0.0")) {
                val type = object : TypeReference<ReserveUserResponse>() {}
                val happy = jackson.readValue(response.body, type)
                val self = WebMvcLinkBuilder.linkTo(UserResource::class.java).slash(happy.id).withSelfRel()
                val hal = HalModelBuilder.halModelOf(happy).link(self).build()
                ResponseEntity.ok(hal)
            }
            else {
                ResponseEntity.internalServerError().build()
            }
        }
        catch (e: Exception) {
            // contents depends on the exception
            val problem = Problem.create()
                                 .withType(URI("https://some-error.example.com"))
                                 .withTitle("some title")
                                 .withDetail("some detail")
                                 .withInstance(URI("https://some-instance.exmaple.com"))
                                 .withStatus(HttpStatus.CONFLICT)
            ResponseEntity.status(HttpStatus.CONFLICT).body(problem)
        }
    }

    @GetMapping(path = ["/{id}"], produces = [MediaTypes.HAL_JSON_VALUE])
    fun read(builder: UriComponentsBuilder, @PathVariable id: String): ResponseEntity<RepresentationModel<out RepresentationModel<*>>> {
        //TODO: do the work
        val self = WebMvcLinkBuilder.linkTo(UserResource::class.java).slash(id).withSelfRel()
        val response = ReserveUserResponse(payload = ReserveUserResponse.Payload("username", password = "password"))
        val hal = HalModelBuilder.halModelOf(response.payload).link(self).build()
        return ResponseEntity.ok(hal)
    }

    @PutMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaTypes.HAL_JSON_VALUE])
    fun update(builder: UriComponentsBuilder, @PathVariable id: String, @RequestBody body: ReserveUserCommand.Payload): ResponseEntity<RepresentationModel<out RepresentationModel<*>>> {
        //TODO: do the work
        val response = ReserveUserResponse(payload = ReserveUserResponse.Payload(body.username, password = "password"))
        val self = WebMvcLinkBuilder.linkTo(UserResource::class.java).slash(id).withSelfRel()
        val hal = HalModelBuilder.halModelOf(response.payload).link(self).build()
        return ResponseEntity.ok(hal)
    }

    @DeleteMapping(path = ["/{id}"],produces = [MediaTypes.HAL_JSON_VALUE])
    fun delete(builder: UriComponentsBuilder, @PathVariable id: String): ResponseEntity<RepresentationModel<out RepresentationModel<*>>> {
        //TODO: do the work
        val self = WebMvcLinkBuilder.linkTo(UserResource::class.java).slash(id).withSelfRel()
        val response = ReserveUserResponse(payload = ReserveUserResponse.Payload("username", password = "password"))
        val hal = HalModelBuilder.halModelOf(response.payload).link(self).build()
        return ResponseEntity.ok(hal)
    }
}