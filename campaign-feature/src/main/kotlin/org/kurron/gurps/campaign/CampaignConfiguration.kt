package org.kurron.gurps.campaign

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
class CampaignConfiguration {
    @Bean
    fun campaignCommandQueue(): Queue = QueueBuilder.durable("campaign-commands").build()

    @Bean
    fun campaignCommandBinding(pointToPointExchange: DirectExchange, campaignCommandQueue: Queue): Binding = BindingBuilder.bind(campaignCommandQueue).to(pointToPointExchange).with(SharedConfiguration.CAMPAIGN_COMMAND_KEY)

    @Bean
    fun campaignEventQueue(): Queue = QueueBuilder.durable("campaign-events").build()

    @Bean
    fun campaignEventBinding(pointToMultiPointExchange: TopicExchange, campaignEventQueue: Queue): Binding = BindingBuilder.bind(campaignEventQueue).to(pointToMultiPointExchange).with(SharedConfiguration.CAMPAIGN_EVENT_KEY)

    @Bean
    fun campaignCommandListener(rabbitmq: RabbitOperations, jackson: ObjectMapper): MessageListener = CampaignCommandListener(rabbitmq, jackson)

    @Bean
    fun campaignCommandContainer(connectionFactory: ConnectionFactory, campaignCommandQueue: Queue, campaignCommandListener: MessageListener): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueues(campaignCommandQueue)
        container.setMessageListener(campaignCommandListener)
        container.setBatchSize(1) // probably want commands to come in one at a time, to help preserve some semblance of ordering
        return container
    }
}