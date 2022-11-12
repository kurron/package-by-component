package org.kurron.gurps.processor.cli

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringBeans {
    @Bean
    fun messageConverter(): MessageConverter = Jackson2JsonMessageConverter()
}