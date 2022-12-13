package org.kurron.gurps.character

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
class CharacterConfiguration {
    @Bean
    fun characterCommandQueue(): Queue = QueueBuilder.durable("character-commands").build()

    @Bean
    fun characterCommandBinding(pointToPointExchange: DirectExchange, characterCommandQueue: Queue): Binding = BindingBuilder.bind(characterCommandQueue).to(pointToPointExchange).with(SharedConfiguration.CHARACTER_COMMAND_KEY)

    @Bean
    fun characterEventQueue(): Queue = QueueBuilder.durable("character-events").build()

    @Bean
    fun characterEventBinding(pointToMultiPointExchange: TopicExchange, characterEventQueue: Queue): Binding = BindingBuilder.bind(characterEventQueue).to(pointToMultiPointExchange).with(SharedConfiguration.CHARACTER_EVENT_KEY)

    @Bean
    fun characterCommandListener(rabbitmq: RabbitOperations, jackson: ObjectMapper): MessageListener = CharacterCommandListener(rabbitmq, jackson)

    @Bean
    fun characterCommandContainer(connectionFactory: ConnectionFactory, characterCommandQueue: Queue, characterCommandListener: MessageListener): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueues(characterCommandQueue)
        container.setMessageListener(characterCommandListener)
        container.setBatchSize(1) // probably want commands to come in one at a time, to help preserve some semblance of ordering
        return container
    }
}