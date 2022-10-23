package org.kurron.gurps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Integration point between Cucumber and Spring.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = SpringBeans.class)
public class CucumberTestContextConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CucumberTestContextConfiguration.class);

    // NOTE: this function must be in this file or Spring doesn't pick it up. See this for details: https://spring.io/blog/2020/03/27/dynamicpropertysource-in-spring-framework-5-2-5-and-spring-boot-2-2-6
    /**
     * Called after Cucumber starts but before Spring does. Gives us the ability to store connection information to the various containers.
     * @param registry where to add to Spring configuration.
     */
    @DynamicPropertySource
    static void calledBeforeSpringButAfterTestcontainers(DynamicPropertyRegistry registry) {
        LOGGER.debug("calledBeforeSpringButAfterTestcontainers called.");
        //Containers.registerContainerCoordinates(registry);
    }
}
