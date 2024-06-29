package org.kurron.gurps.asset

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("Asset")
internal class PrivateImplementation(private val events: ApplicationEventPublisher, private val repository: ArmorRepository): ProvidedInterface {

    @Transactional
    override fun foo(): String {
        val primaryKey = repository.save(Armor.randomInstance())
        events.publishEvent("Just persisted asset " + primaryKey.id)
        return "org.kurron.gurps.asset.PrivateImplementation called!"
    }
}