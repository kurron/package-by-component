package org.kurron.gurps.user

import org.springframework.stereotype.Component

@Component("User")
internal class PrivateImplementation: ProvidedInterface {
    override fun foo() = "org.kurron.gurps.user.PrivateImplementation called!"
}