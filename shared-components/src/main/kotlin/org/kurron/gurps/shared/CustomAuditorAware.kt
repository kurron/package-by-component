package org.kurron.gurps.shared

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import java.util.Optional


class CustomAuditorAware: AuditorAware<User> {
    override fun getCurrentAuditor(): Optional<User> {
        return Optional.ofNullable(SecurityContextHolder.getContext()!!)
                       .map { it.authentication }
                       .filter{ it.isAuthenticated }
                       .map { it.principal }
                       .map { it as User }
    }
}