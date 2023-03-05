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
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer
import org.springframework.amqp.rabbit.listener.MessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.util.ErrorHandler


@Configuration
@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL, EnableHypermediaSupport.HypermediaType.HTTP_PROBLEM_DETAILS])
class UserConfiguration {
    @Bean
    fun userCommandQueue(): Queue = QueueBuilder.durable("user-commands")
                                                .deadLetterExchange(SharedConfiguration.DEAD_LETTER_EXCHANGE)
                                                .deadLetterRoutingKey(SharedConfiguration.DEAD_LETTER_KEY)
                                                .overflow(QueueBuilder.Overflow.rejectPublish)
                                                .build()

    @Bean
    fun userCommandBinding(pointToPointExchange: DirectExchange, userCommandQueue: Queue): Binding = BindingBuilder.bind(userCommandQueue).to(pointToPointExchange).with(SharedConfiguration.USER_COMMAND_KEY)

    @Bean
    fun userEventQueue(): Queue = QueueBuilder.durable("user-events")
                                              .deadLetterExchange(SharedConfiguration.DEAD_LETTER_EXCHANGE)
                                              .deadLetterRoutingKey(SharedConfiguration.DEAD_LETTER_KEY)
                                              .overflow(QueueBuilder.Overflow.dropHead)
                                              .build()

    @Bean
    fun userEventBinding(pointToMultiPointExchange: TopicExchange, userEventQueue: Queue): Binding = BindingBuilder.bind(userEventQueue).to(pointToMultiPointExchange).with(SharedConfiguration.USER_EVENT_KEY)

    @Bean
    fun userCommandListener(rabbitmq: RabbitOperations, jackson: ObjectMapper, repository: AccountRepository): MessageListener = UserCommandListener(rabbitmq, jackson, repository)

    @Bean
    fun userCommandContainer(connectionFactory: ConnectionFactory,
                             userCommandQueue: Queue,
                             userCommandListener: MessageListener,
                             conditionalRejectingErrorHandler: ErrorHandler): MessageListenerContainer {
        val container = DirectMessageListenerContainer(connectionFactory)
        container.setQueues(userCommandQueue)
        container.setMessageListener(userCommandListener)
        container.setErrorHandler(conditionalRejectingErrorHandler)
        container.setObservationEnabled(true)
        //container.setBatchSize(1) // probably want commands to come in one at a time, to help preserve some semblance of ordering
        return container
    }

}