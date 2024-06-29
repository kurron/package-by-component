package org.kurron.gurps.asset

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.core.io.FileSystemResource
import org.springframework.data.jdbc.core.mapping.schema.LiquibaseChangeSetWriter
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.data.repository.config.BootstrapMode
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File

@Testcontainers
@SpringBootTest
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
    }

    @Autowired
    private lateinit var sut: AssetRepository

    @Test
    fun verifyWrite() {
        val running = postgresql.isRunning()
        val all = sut.findAll()
        val i = 0
    }
}