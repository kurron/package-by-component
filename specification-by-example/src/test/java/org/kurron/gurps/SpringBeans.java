package org.kurron.gurps;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Spring configuration to help with sharing state between steps.
 */
@Configuration
public class SpringBeans {

    @Bean
    public TestingContext testingContext(Environment environment, AmqpTemplate rabbit) {
        return new TestingContext(environment, rabbit);
    }

    @Bean
    public MessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }
}
