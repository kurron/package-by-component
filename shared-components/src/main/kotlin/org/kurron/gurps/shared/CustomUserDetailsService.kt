package org.kurron.gurps.shared

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomUserDetailsService: UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        // TODO: look up users in the database
        return when(username) {
            "gary" -> CustomUserDetails("gary", "yrag")
            "penny" -> CustomUserDetails("penny", "ynnep")
            "adam" -> CustomUserDetails("adam", "mada")
            else -> throw UsernameNotFoundException("Username $username is not in the system!")
        }
    }
}