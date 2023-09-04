package org.kurron.gurps.user

import org.springframework.stereotype.Component

@Component("User")
internal class PrivateImplementation(private val repository: AccountRepository): ProvidedInterface {
    override fun foo() : String {
        val primaryKey = repository.save(Account.randomInstance())
        return "org.kurron.gurps.user.PrivateImplementation called! Just created a new account with primary key of  $primaryKey"
    }
}