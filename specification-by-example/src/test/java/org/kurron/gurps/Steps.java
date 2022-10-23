package org.kurron.gurps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Steps {
    private static final Logger LOGGER = LoggerFactory.getLogger(Steps.class);

    @Given("some prerequisite")
    public void some_prerequisite() {
        LOGGER.debug("some_prerequisite called.");
    }

    @When("an action is taken")
    public void an_action_is_taken() {
        LOGGER.debug("an_action_is_taken called.");
    }

    @Then("an expected result is detected")
    public void an_expected_result_is_detected() {
        LOGGER.debug("an_expected_result_is_detected called.");
    }
}
