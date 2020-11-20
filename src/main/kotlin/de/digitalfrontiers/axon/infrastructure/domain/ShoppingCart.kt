package de.digitalfrontiers.axon.infrastructure.domain

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
class ShoppingCart {

    @AggregateIdentifier
    var id: UUID? = null

    val productItems = mutableMapOf<UUID, Long>()


    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    fun addToCart(command: AddToShoppingCartCommand) {
        if (id == null) {
            AggregateLifecycle.apply(
                ShoppingCartCreatedEvent(shoppingCartId = command.shoppingCartId)
            )
        }

        AggregateLifecycle.apply(
            ProductItemAddedEvent(
                shoppingCartId = command.shoppingCartId,
                productItemId = command.productItemId,
                quantity = command.quantity
            )
        )
    }

    @EventSourcingHandler
    fun on(event: ShoppingCartCreatedEvent) {
        id = event.shoppingCartId
    }

    @EventSourcingHandler
    fun on(event: ProductItemAddedEvent) {
        productItems.merge(event.productItemId, event.quantity, Math::addExact)
    }

    @CommandHandler
    fun placeOrder(command: PlaceOrderCommand) {
        val orderId = UUID.randomUUID()

        AggregateLifecycle.apply(
            ShoppingCartOrderedEvent(
                shoppingCartId = command.shoppingCartId,
                orderId = orderId
            )
        )

        AggregateLifecycle.createNew(Order::class.java) {
            Order(
                id = orderId,
                productItems = productItems
            )
        }
    }

    @EventSourcingHandler
    fun on(event: ShoppingCartOrderedEvent) {
        AggregateLifecycle.markDeleted()
    }
}
