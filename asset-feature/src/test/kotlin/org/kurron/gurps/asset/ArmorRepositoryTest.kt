package org.kurron.gurps.asset

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration
import kotlin.test.assertTrue

@Testcontainers
@SpringBootTest(classes = [ArmorRepositoryTest.Companion.AdditionalBeans::class])
@ActiveProfiles(profiles = ["test"])
class ArmorRepositoryTest {
    companion object {
        @Container
        @JvmStatic
        val postgresql = PostgreSQLContainer("postgres:16-alpine")

        /**
         * We want to have an ephemeral database, so we cannot use the more convenient @ServiceConnection annotation to configure things for us.
         */
        @DynamicPropertySource
        @JvmStatic
        fun postgresqlProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { "${postgresql.getJdbcUrl()}&TC_TMPFS=/testtmpfs:rw" }
        }

        @TestConfiguration
        class AdditionalBeans {
        }
    }

    @Autowired
    private lateinit var sut: ArmorRepository

    @Test
    fun verifyWrite() {
        val running = postgresql.isRunning()
        assertTrue(running, "Database is not running!")
        val toSave = Armor(type = "Cloth Armor", damageResistance = 0, cost = 150, weight = 12)
        val written = sut.save(toSave)
        val read = sut.findById(written.id)
        val toUpdate = read.get().copy(damageResistance = 1)
        Thread.sleep(Duration.ofSeconds(2))
        sut.save(toUpdate)
        val all = sut.findAll()
        val i = 0
    }
}
