package org.kurron.gurps;

import org.springframework.core.env.Environment;

public class TestingContext {
    /**
     * Spring's composition of all configuration sources it is aware of.
     */
    private final Environment environment;

    public TestingContext(Environment environment) {
        this.environment = environment;
    }
}
