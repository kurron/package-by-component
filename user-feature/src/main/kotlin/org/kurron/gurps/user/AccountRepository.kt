package org.kurron.gurps.user

import org.springframework.data.repository.ListCrudRepository

internal interface AccountRepository: ListCrudRepository<Account, Int>