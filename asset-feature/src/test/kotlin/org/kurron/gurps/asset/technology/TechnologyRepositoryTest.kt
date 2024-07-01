package org.kurron.gurps.asset.technology

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.kurron.gurps.asset.shield.Shield
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
import java.util.concurrent.ThreadLocalRandom

@Testcontainers
@SpringBootTest(classes = [TechnologyRepositoryTest.Companion.AdditionalBeans::class])
@ActiveProfiles(profiles = ["test"])
class TechnologyRepositoryTest {
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
        class AdditionalBeans
    }

    @Autowired
    private lateinit var sut: TechnologyRepository

    @Test
    @DisplayName("Verify CRUD operations")
    fun verifyCRUD() {
        val running = postgresql.isRunning()
        assertTrue(running, "Database is not running!")
        val toSave = TechnologyLevel(level = "Stone Age", description = "Prehistory and later", startingWealth = 250)
        val written = sut.save(toSave)
        val read = sut.findById(written.id)
        val startingWealth = ThreadLocalRandom.current().nextInt(Int.MAX_VALUE)
        val toUpdate = read.get().copy(startingWealth = startingWealth)
        Thread.sleep(Duration.ofSeconds(2))
        sut.save(toUpdate)
        val all = sut.findAll()
        assertEquals(1, all.size, "Unexpected result size!")
        assertEquals(startingWealth, all.first().startingWealth, "Starting Wealth do not match!")
        sut.delete(all.first())
        val found = sut.findAll()
        assertTrue(found.isEmpty(), "Deletion did not work!")
    }
}
