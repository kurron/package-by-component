package org.kurron.gurps.asset

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.test.assertTrue

@Testcontainers
@SpringBootTest
@Import(AssetRepositoryTest.Companion.AdditionalBeans::class)
@EnableJdbcAuditing //TODO: enable this everywhere?
class AssetRepositoryTest {
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
            @Bean
            fun fauxAuditor(): AuditorAware<String> = FauxAuditor()
        }
    }

    @Autowired
    private lateinit var sut: AssetRepository

    @Test
    fun verifyWrite() {
        val running = postgresql.isRunning()
        assertTrue(running, "Database is not running!")
        val toSave = Asset(type = "Cloth Armor", damageResistant = 0, cost = 150, weight = 12)
        val written = sut.save(toSave)
        val read = sut.findById(written.id)
        val toUpdate = read.get().copy(damageResistant = 1)
        Thread.sleep(Duration.ofSeconds(2))
        sut.save(toUpdate)
        val all = sut.findAll()
        val i = 0
    }
}

// pretends to know how to locate the currently authenticated in user
class FauxAuditor : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        return Optional.of(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE).toString(16).uppercase())
    }
}


