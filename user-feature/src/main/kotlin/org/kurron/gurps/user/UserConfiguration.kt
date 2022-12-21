package org.kurron.gurps.user

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
import org.springframework.web.filter.ForwardedHeaderFilter


@Configuration
class UserConfiguration {
    @Bean
    fun userCommandQueue(): Queue = QueueBuilder.durable("user-commands").build()

    @Bean
    fun userCommandBinding(pointToPointExchange: DirectExchange, userCommandQueue: Queue): Binding = BindingBuilder.bind(userCommandQueue).to(pointToPointExchange).with(SharedConfiguration.USER_COMMAND_KEY)

    @Bean
    fun userEventQueue(): Queue = QueueBuilder.durable("user-events").build()

    @Bean
    fun userEventBinding(pointToMultiPointExchange: TopicExchange, userEventQueue: Queue): Binding = BindingBuilder.bind(userEventQueue).to(pointToMultiPointExchange).with(SharedConfiguration.USER_EVENT_KEY)

    @Bean
    fun userCommandListener(rabbitmq: RabbitOperations, jackson: ObjectMapper): MessageListener = UserCommandListener(rabbitmq, jackson)

    @Bean
    fun userCommandContainer(connectionFactory: ConnectionFactory, userCommandQueue: Queue, userCommandListener: MessageListener): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueues(userCommandQueue)
        container.setMessageListener(userCommandListener)
        container.setBatchSize(1) // probably want commands to come in one at a time, to help preserve some semblance of ordering
        return container
    }

    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter {
        return ForwardedHeaderFilter()
    }

}