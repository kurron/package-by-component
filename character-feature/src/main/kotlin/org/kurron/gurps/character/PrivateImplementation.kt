package org.kurron.gurps.character

import org.springframework.stereotype.Component

@Component("Character")
internal class PrivateImplementation: ProvidedInterface {
    override fun foo() = "org.kurron.gurps.character.PrivateImplementation called!"
}