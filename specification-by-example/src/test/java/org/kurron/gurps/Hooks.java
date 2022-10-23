package org.kurron.gurps;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Functions that get called at various points in the Cucumber lifecycle. This is just to showcase what is possible. The annotations can go into other objects. Don't feel you have to force logic into this file.
 */
public class Hooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void beforeFirstStepOfTheScenario() {
        LOGGER.warn("beforeFirstStepOfTheScenario called");
    }

    @After
    public void afterLastStepOfTheScenario() {
        LOGGER.warn("afterLastStepOfTheScenario called");
    }

    @BeforeStep
    public void beforeEachStep() {
        LOGGER.warn("beforeEachStep called");
    }

    @AfterStep
    public void afterEachStep() {
        LOGGER.warn("afterEachStep called");
    }

    @BeforeAll
    public static void beforeAllScenarios(){
        LOGGER.error("beforeAllScenarios called");
    }

    @AfterAll
    public static void afterAllScenarios(){
        LOGGER.error("afterAllScenarios called");
    }

    @After
    public void afterEachScenario() {
        LOGGER.error("afterEachScenario called");
    }

    @Before
    public void beforeEachScenario() {
        LOGGER.error("beforeEachScenario called");
    }

}
