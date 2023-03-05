package org.kurron.gurps.shared

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.util.ErrorHandler
import org.springframework.web.filter.ForwardedHeaderFilter
import java.net.URI

@Configuration
@EnableJdbcAuditing
class SharedConfiguration {
    companion object {
        // Messaging constants
        const val APPLICATION_ID = "GURPS Online"
        const val COMMAND_EXCHANGE = "point-to-point"
        const val DEAD_LETTER_EXCHANGE = "dead-letter"
        const val EVENT_EXCHANGE = "point-to-multi-point"
        const val DEAD_LETTER_KEY = "dead.letter"
        const val ASSET_COMMAND_KEY = "command.asset"
        const val ASSET_EVENT_KEY = "event.asset"
        const val CAMPAIGN_COMMAND_KEY = "command.campaign"
        const val CAMPAIGN_EVENT_KEY = "event.campaign"
        const val CHARACTER_COMMAND_KEY = "command.character"
        const val CHARACTER_EVENT_KEY = "event.character"
        const val USER_COMMAND_KEY = "command.user"
        const val USER_EVENT_KEY = "event.user"

        // AMQP Headers
        const val PRIMARY_KEY = "primary-key"
        const val COMMAND_LABEL_KEY = "command-label"
        const val CREATE_USER_LABEL = "command.user.create-user"

        // Problem details
        val BLANK_TYPE = URI.create("about:blank")
        val EXAMPLE_TYPE = URI.create("https://example.net/validation-error")
        val EXAMPLE_INSTANCE = URI.create("https://gurps.test-bed-alpha.example.com/account/12345/msgs/abc")
    }

    @Bean
    fun deadLetterExchange(): DirectExchange = ExchangeBuilder.directExchange(DEAD_LETTER_EXCHANGE).durable(false).build()

    @Bean
    fun pointToPointExchange(deadLetterExchange: DirectExchange): DirectExchange = ExchangeBuilder.directExchange(COMMAND_EXCHANGE).durable(false).build()

    @Bean
    fun pointToMultiPointExchange(): TopicExchange = ExchangeBuilder.topicExchange(EVENT_EXCHANGE).durable(false).build()

    @Bean
    fun deadLetterQueue(): Queue = QueueBuilder.durable("dead-letter").overflow(QueueBuilder.Overflow.dropHead).build()

    @Bean
    fun deadLetterBinding(deadLetterExchange: DirectExchange, deadLetterQueue: Queue): Binding = BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DEAD_LETTER_KEY)

    @Bean
    fun converter(jackson: ObjectMapper): Jackson2JsonMessageConverter = Jackson2JsonMessageConverter(jackson)

    @Bean
    fun customExceptionStrategy(): FatalExceptionStrategy = CustomExceptionStrategy()

    @Bean
    fun conditionalRejectingErrorHandler(customExceptionStrategy: FatalExceptionStrategy): ErrorHandler = ConditionalRejectingErrorHandler(customExceptionStrategy)

    @Bean
    fun customAuditorAware(): CustomAuditorAware = CustomAuditorAware()

    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter {
        return ForwardedHeaderFilter()
    }

    @Bean
    fun customUserDetailsService(): CustomUserDetailsService = CustomUserDetailsService()

    @Bean
    fun daoAuthenticationProvider(customUserDetailsService: CustomUserDetailsService): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(customUserDetailsService)
        // TODO: use a real password encoding strategy
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance())
        return provider
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests{it.anyRequest().authenticated()}.csrf().disable().httpBasic()
        return http.build()
    }
}