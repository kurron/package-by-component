package org.kurron.gurps.asset

import com.fasterxml.jackson.databind.ObjectMapper
import org.kurron.gurps.shared.SharedConfiguration
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AssetConfiguration {
    @Bean
    fun assetCommandQueue(): Queue = QueueBuilder.durable("asset-commands").build()

    @Bean
    fun assetCommandBinding(pointToPointExchange: DirectExchange, assetCommandQueue: Queue): Binding = BindingBuilder.bind(assetCommandQueue).to(pointToPointExchange).with(SharedConfiguration.ASSET_COMMAND_KEY)

    @Bean
    fun assetEventQueue(): Queue = QueueBuilder.durable("asset-events").build()

    @Bean
    fun assetEventBinding(pointToMultiPointExchange: TopicExchange, assetEventQueue: Queue): Binding = BindingBuilder.bind(assetEventQueue).to(pointToMultiPointExchange).with(SharedConfiguration.ASSET_EVENT_KEY)

    @Bean
    fun assetCommandListener(rabbitmq: RabbitOperations, jackson: ObjectMapper): MessageListener = AssetCommandListener(rabbitmq, jackson)

    @Bean
    fun assetCommandContainer(connectionFactory: ConnectionFactory, assetCommandQueue: Queue, assetCommandListener: MessageListener): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueues(assetCommandQueue)
        container.setMessageListener(assetCommandListener)
        container.setBatchSize(1) // probably want commands to come in one at a time, to help preserve some semblance of ordering
        return container
    }
}