package org.kurron.gurps.shared

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.core.MessagePropertiesBuilder
import org.springframework.hateoas.mediatype.problem.Problem
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.time.Instant
import java.util.Date
import java.util.UUID


/**
 * Our take on the RFC 7807 problem detail specification. Can be transformed into an AMQP message.
 */
data class FailureDetails(@JsonProperty("detail") val detail: String, // Your current balance is 30, but that costs 50. (occurrence specific details)
                          @JsonProperty("instance") val instance: URI, // /account/12345/messages/abc (specific occurrence)
                          @JsonProperty("failure-type") val failureType: FailureType = FailureType.DEFAULT_FAILURE_TYPE,
                          @JsonProperty("properties") val properties: Map<String,String> = emptyMap(), // "accounts": ["/account/12345", "/account/67890"] (format extensions)
                          @JsonProperty("structure") val structure: MessageStructure = MessageStructure(version = "1.0.0", type = "failure", feature = "all")) {
    companion object {
        fun randomInstance(): FailureDetails = FailureDetails("You have randomly failed!", URI.create("problems/randomly-generated"))
    }

    fun toMessage(jackson: ObjectMapper, correlationId: String): Message {
        val bytes = jackson.writeValueAsBytes(this)
        val now = Date.from(Instant.now())
        val type = "${structure.type}/${structure.feature}/${structure.version}"
        val properties = MessagePropertiesBuilder.newInstance()
                                                 .setAppId(SharedConfiguration.APPLICATION_ID)
                                                 .setMessageId(UUID.randomUUID().toString())
                                                 .setTimestamp(now)
                                                 .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                                                 .setType(type)
                                                 .setCorrelationId(correlationId)
                                                 .build()
        return MessageBuilder.withBody(bytes).andProperties(properties).build()
    }

    fun toProblem(builder: UriComponentsBuilder): Problem {
        return Problem.create()
                      .withType(builder.replacePath(failureType.type.path).build().toUri())
                      .withTitle(failureType.title)
                      .withStatus(failureType.status)
                      .withDetail(detail)
                      .withInstance(instance)
                      .withProperties(properties)
    }
}

data class FailureType(@JsonProperty("type") val type: URI, @JsonProperty("title") val title: String, @JsonProperty("status") val status: HttpStatus) {
    companion object {
        val DEFAULT_FAILURE_TYPE:  FailureType = FailureType(URI.create("about:blank"), "No further details", HttpStatus.INTERNAL_SERVER_ERROR)
        val NULL_JPA_ENTITY:  FailureType = FailureType(URI.create("problems/null-jpa-entity"), "Null JPA Entity", HttpStatus.INTERNAL_SERVER_ERROR)
        val OPTIMISTIC_LOCKING_FAILURE:  FailureType = FailureType(URI.create("problems/optimistic-locking-failure"), "Optimistic Locking Failure", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

