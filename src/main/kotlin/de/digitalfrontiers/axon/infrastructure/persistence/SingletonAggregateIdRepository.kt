package de.digitalfrontiers.axon.infrastructure.persistence

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SingletonAggregateIdRepository : CrudRepository<SingletonAggregateId, String>
