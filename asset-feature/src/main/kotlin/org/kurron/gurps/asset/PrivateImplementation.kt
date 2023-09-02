package org.kurron.gurps.asset

import org.springframework.stereotype.Component

@Component("Asset")
internal class PrivateImplementation: ProvidedInterface {
    override fun foo() =  "org.kurron.gurps.asset.PrivateImplementation called!"
}