package de.digitalfrontiers.axon.infrastructure.tracking

import de.digitalfrontiers.axon.infrastructure.persistence.SingletonAggregateId
import de.digitalfrontiers.axon.infrastructure.persistence.SingletonAggregateIdRepository
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("infrastructure")
class InfrastructureCreationEventHandler(
    private val singletonAggregateIdRepository: SingletonAggregateIdRepository
) {

    @EventHandler
    fun on(event: InfrastructureCreatedEvent) {
        singletonAggregateIdRepository.save(
            SingletonAggregateId(
                type = InfrastructureAggregate.TYPE,
                aggregateIdentifier = event.id.toString()
            )
        )
    }
}
