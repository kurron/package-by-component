package org.kurron.gurps.asset

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("Asset")
internal class PrivateImplementation(private val events: ApplicationEventPublisher): ProvidedInterface {

    @Transactional
    override fun foo(): String {
        events.publishEvent("Ron was here!")
        return "org.kurron.gurps.asset.PrivateImplementation called!"
    }
}