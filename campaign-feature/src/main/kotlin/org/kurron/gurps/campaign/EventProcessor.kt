package org.kurron.gurps.campaign

import org.slf4j.LoggerFactory
import org.springframework.modulith.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
internal class EventProcessor {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @ApplicationModuleListener
    fun on(event: String) {
        logger.info("Just heard: {}", event)
    }
}