package org.kurron.gurps.shared

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SharedConfiguration {
    companion object {
        const val APPLICATION_ID = "GURPS Online"
        const val COMMAND_EXCHANGE = "point-to-point"
        const val EVENT_EXCHANGE = "point-to-multi-point"
        const val ASSET_COMMAND_KEY = "command.asset"
        const val ASSET_EVENT_KEY = "event.asset"
    }

    @Bean
    fun pointToPointExchange(): DirectExchange = ExchangeBuilder.directExchange(COMMAND_EXCHANGE).durable(false).build()

    @Bean
    fun pointToMultiPointExchange(): TopicExchange = ExchangeBuilder.topicExchange(EVENT_EXCHANGE).durable(false).build()

    @Bean
    fun converter(jackson: ObjectMapper): Jackson2JsonMessageConverter = Jackson2JsonMessageConverter(jackson)
}