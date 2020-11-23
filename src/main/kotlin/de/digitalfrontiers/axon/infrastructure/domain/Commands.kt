package de.digitalfrontiers.axon.infrastructure.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class AddToShoppingCartCommand(
    @TargetAggregateIdentifier
    val shoppingCartId: UUID,
    val productItemId: UUID,
    val quantity: Long
)

data class PlaceOrderCommand(
    @TargetAggregateIdentifier
    val shoppingCartId: UUID
)
