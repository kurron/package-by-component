package org.kurron.gurps.asset

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
internal class SeedData(private val repository: ArmorRepository) : CommandLineRunner {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    override fun run(vararg args: String?) {
        logger.info("Seeding Armor data...")
        repository.save(Armor(type = "Cloth Armor", damageResistance = 1, cost = 150, weight = 12))
        repository.save(Armor(type = "Leather Armor", damageResistance = 2, cost = 340, weight = 20))
        repository.save(Armor(type = "Light Scale", damageResistance = 3, cost = 610, weight = 49))
        repository.save(Armor(type = "Mail", damageResistance = 4, cost = 645, weight = 58))
        repository.save(Armor(type = "Steel Laminate", damageResistance = 5, cost = 1_360, weight = 64))
        repository.save(Armor(type = "Plate", damageResistance = 6, cost = 4_040, weight = 90))
        repository.save(Armor(type = "Flak Jacket", damageResistance = 7, cost = 500, weight = 20))
        repository.save(Armor(type = "Ballistic Vest", damageResistance = 8, cost = 400, weight = 2))
        repository.save(Armor(type = "Tactical Vest", damageResistance = 12, cost = 900, weight = 9))
    }
}