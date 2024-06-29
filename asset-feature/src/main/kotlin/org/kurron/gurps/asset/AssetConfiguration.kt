package org.kurron.gurps.asset

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@Configuration
@EnableJdbcAuditing
class AssetConfiguration {
    // pretends to know how to locate the currently authenticated user
    @Bean
    fun fauxAuditor(): AuditorAware<String> = AuditorAware<String> { Optional.of(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE).toString(16).uppercase()) }
}