package org.kurron.gurps.asset.technology

import org.springframework.data.repository.ListCrudRepository

internal interface TechnologyRepository: ListCrudRepository<TechnologyLevel, Int>