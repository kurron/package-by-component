package org.kurron.gurps.asset.shield

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@Testcontainers
@DataJdbcTest
@Import(ShieldRepositoryTest.Companion.AdditionalBeans::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = ["test"])
class ShieldRepositoryTest {
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
        @EnableJdbcAuditing
        class AdditionalBeans {
            // pretends to know how to locate the currently authenticated user
            @Bean
            fun fauxAuditor(): AuditorAware<String> = AuditorAware<String> { Optional.of(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE).toString(16).uppercase()) }
        }
    }

    @Autowired
    private lateinit var sut: ShieldRepository

    @Autowired
    private lateinit var database: JdbcAggregateTemplate

    @Test
    @DisplayName("Verify CRUD operations")
    fun verifyCRUD() {
        val running = postgresql.isRunning()
        assertTrue(running, "Database is not running!")
        val toSave = Shield(type = "Small Shield", defenseBonus = 0, cost = 40, weight = 8)
        val written = sut.save(toSave)
        val read = sut.findById(written.id)
        val defenseBonus = ThreadLocalRandom.current().nextInt(Int.MAX_VALUE)
        val toUpdate = read.get().copy(defenseBonus = defenseBonus)
        Thread.sleep(Duration.ofSeconds(2))
        val updated = sut.save(toUpdate)
        val found = database.findById(updated.id, Shield::class.java)
        assertEquals(defenseBonus, found.defenseBonus, "Defense Bonuses do no match!")
        sut.delete(found)
        assertNull(database.findById(toUpdate.id, Shield::class.java), "Row still exists!")
    }
}
