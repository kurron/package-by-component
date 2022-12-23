package org.kurron.gurps

import org.kurron.gurps.user.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.filter.ForwardedHeaderFilter


@Configuration
class ApplicationConfiguration {
    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter {
        return ForwardedHeaderFilter()
    }

    @Bean
    fun customUserDetailsService(): CustomUserDetailsService = CustomUserDetailsService()

    @Bean
    fun daoAuthenticationProvider(customUserDetailsService: CustomUserDetailsService): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(customUserDetailsService)
        // TODO: use a real password encoding strategy
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance())
        return provider
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests{it.anyRequest().authenticated()}.csrf().disable().httpBasic()
        return http.build()
    }
}