package org.kurron.gurps.processor.command

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler
import java.time.Instant

@Configuration
class SpringBeans {
    @Bean
    fun commandChannel(): MessageChannel? = DirectChannel()

    @Bean
    fun inbound(listenerContainer: SimpleMessageListenerContainer, @Qualifier("commandChannel") channel: MessageChannel): AmqpInboundChannelAdapter {
        val adapter = AmqpInboundChannelAdapter(listenerContainer)
        adapter.outputChannel = channel
        return adapter
    }

    @Bean
    fun commandQueue(): Queue = Queue("gurp-commands", false, false, false)

    @Bean
    fun container(connectionFactory: ConnectionFactory, commandQueue: Queue): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer(connectionFactory)
        container.setQueues(commandQueue)
        container.setConcurrentConsumers(2)
        return container
    }

    @Bean
    @ServiceActivator(inputChannel = "commandChannel")
    fun handler(): MessageHandler = MessageHandler { message -> println(message.payload) }

    @Bean
    @ServiceActivator(inputChannel = "commandChannel")
    fun amqpOutbound(amqpTemplate: AmqpTemplate): AmqpOutboundEndpoint {
        val outbound = AmqpOutboundEndpoint(amqpTemplate)
        outbound.setRoutingKey("gurp-commands") // default exchange - route to queue 'gurp-commands'
        return outbound
    }

    @Bean
    fun fireOffMessage(template: AmqpTemplate): CommandLineRunner = CommandLineRunner { template.convertAndSend("gurp-commands", "Command: ${Instant.now()}") }
}