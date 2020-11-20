package de.digitalfrontiers.axon.infrastructure.domain

import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
class Order {

    @AggregateIdentifier
    lateinit var id: UUID
    lateinit var productItems: Map<UUID, Long>

    constructor() {}

    constructor(
        id: UUID,
        productItems: Map<UUID, Long>
    ) {
        AggregateLifecycle.apply(
            OrderPlacedEvent(
                orderId = id,
                productItems = productItems
            )
        )
    }

    @EventSourcingHandler
    fun on(event: OrderPlacedEvent) {
        id = event.orderId
        productItems = event.productItems
    }
}
