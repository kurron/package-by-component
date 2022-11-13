package org.kurron.gurps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class Steps {

    @Autowired
    TestingContext testingContext;

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

    @Given("Gary has started creating a new campaign")
    public void gary_has_started_creating_a_new_campaign() {
        Assertions.assertTrue(testingContext.newCampaignStarted(), "Gary has not started creating a new campaign!");
    }
    @When("Gary provides the campaign name")
    public void gary_provides_the_campaign_name() {
        Assertions.assertTrue(testingContext.campaignNameProvided(), "Gary has not provided the campaign name!");
    }
    @Then("the campaign is assigned the specified name")
    public void the_campaign_is_assigned_the_specified_name() {
        Assertions.assertTrue(testingContext.campaignNameSaved(), "Gary's campaign name was not saved!");
    }
}
