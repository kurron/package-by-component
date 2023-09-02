package org.kurron.gurps

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.kurron.gurps.asset.ProvidedInterface as Asset
import org.kurron.gurps.campaign.ProvidedInterface as Campaign
import org.kurron.gurps.character.ProvidedInterface as Character
import org.kurron.gurps.user.ProvidedInterface as User

@Component
class SanityCheck(private val asset: Asset,
                  private val campaign: Campaign,
                  private val character: Character,
                  private val user: User): CommandLineRunner {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    override fun run(vararg args: String) {
        logger.info("Sanity check...")
        logger.info("asset call: " + asset.foo())
        logger.info("campaign call: " + campaign.foo())
        logger.info("character call: " + character.foo())
        logger.info("user call: " + user.foo())
    }
}