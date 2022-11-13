package org.kurron.gurps;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.core.env.Environment;

public class TestingContext {
    /**
     * Spring's composition of all configuration sources it is aware of.
     */
    private final Environment environment;

    private final AmqpTemplate rabbit;

    public TestingContext(Environment environment, AmqpTemplate rabbit) {
        this.environment = environment;
        this.rabbit = rabbit;
    }

    public boolean newCampaignStarted() {
        // what signals the start of a new campaign?
        // new campaign command
        // command
        //   label: command.campaign.new -- easily pattern matched
        //   who: gary
        //   operation: new-campaign
        // needs to wait for a campaign identifier for future use
        // only stored in the in-progress store
        //
        return false;
    }

    public boolean campaignNameProvided() {
        return false;
    }

    public boolean campaignNameSaved() {
        return false;
    }
}
