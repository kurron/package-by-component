package org.kurron.gurps.campaign

import org.springframework.stereotype.Component

@Component("Campaign")
internal class PrivateImplementation: ProvidedInterface {
    override fun foo() = "org.kurron.gurps.campaign.PrivateImplementation called!"
}