package org.kurron.gurps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Spring configuration to help with sharing state between steps.
 */
@Configuration
public class SpringBeans {

    @Bean
    public TestingContext testingContext(Environment environment) {
        return new TestingContext(environment);
    }
}
