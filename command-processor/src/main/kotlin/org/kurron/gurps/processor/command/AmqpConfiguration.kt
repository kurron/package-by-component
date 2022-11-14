package org.kurron.gurps.processor.command

import org.kurron.gurps.shared.*
import org.kurron.gurps.shared.SharedConstants.Companion.MESSAGE_ROUTING_LABEL
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.amqp.inbound.AmqpInboundGateway
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint
import org.springframework.integration.annotation.Router
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.annotation.Splitter
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.router.PayloadTypeRouter
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Headers
import java.time.Instant

/**
 * This object holds configuration beans related to AMQP setup.
 */
@Configuration
class AmqpConfiguration {
    @Bean
    fun commandQueue(): Queue = QueueBuilder.nonDurable(SharedConstants.COMMAND_QUEUE_NAME).build()

    @Bean
    fun commandExchange(): Exchange = ExchangeBuilder.directExchange(SharedConstants.COMMAND_EXCHANGE_NAME).durable(false).build()

    @Bean
    fun commandBinding(commandQueue: Queue, commandExchange: Exchange): Binding = BindingBuilder.bind(commandQueue).to(commandExchange).with(SharedConstants.COMMAND_ROUTING_KEY).noargs()

    @Bean
    fun commandContainer(connectionFactory: ConnectionFactory, commandQueue: Queue): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer(connectionFactory)
        container.setQueues(commandQueue)
        container.setConcurrentConsumers(2)
        return container
    }

    @Bean
    fun eventQueue(): Queue = QueueBuilder.nonDurable(SharedConstants.EVENT_QUEUE_NAME).build()

    @Bean
    fun eventExchange(): Exchange = ExchangeBuilder.fanoutExchange(SharedConstants.EVENT_EXCHANGE_NAME).durable(false).build()

    @Bean
    fun eventBinding(eventQueue: Queue, eventExchange: Exchange): Binding = BindingBuilder.bind(eventQueue).to(eventExchange).with(SharedConstants.EVENT_ROUTING_KEY).noargs()

}