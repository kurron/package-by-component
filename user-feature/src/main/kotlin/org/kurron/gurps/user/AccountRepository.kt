package org.kurron.gurps.user

import org.springframework.data.repository.ListCrudRepository

interface AccountRepository: ListCrudRepository<Account, Int>