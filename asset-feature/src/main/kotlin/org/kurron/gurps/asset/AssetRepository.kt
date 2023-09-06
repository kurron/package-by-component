package org.kurron.gurps.asset

import org.springframework.data.repository.ListCrudRepository

internal interface AssetRepository: ListCrudRepository<Asset, Int>