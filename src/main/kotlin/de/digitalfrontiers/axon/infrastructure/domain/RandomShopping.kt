package de.digitalfrontiers.axon.infrastructure.domain

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.boot.CommandLineRunner
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import kotlin.random.Random


@Component
class RandomShopping(
    private val commandGateway: CommandGateway,
    private val taskScheduler: TaskScheduler
) : CommandLineRunner {

    private val stock =
        (1..10).map { UUID.randomUUID() }

    private fun doShop(cartId: UUID) {
        commandGateway.sendAndWait<Unit>(
            AddToShoppingCartCommand(
                shoppingCartId = cartId,
                productItemId = stock.shuffled().first(),
                quantity = Random.nextLong(from = 1, until = 10)
            )
        )

        when (Random.nextInt(from = 0, until = 10)) {
            in 0..2 -> {
                commandGateway.sendAndWait<Unit>(
                    PlaceOrderCommand(shoppingCartId = cartId)
                )
                triggerShopping(cartId = UUID.randomUUID())
            }
            else -> triggerShopping(cartId = cartId)
        }
    }

    private fun triggerShopping(cartId: UUID) {
        taskScheduler.scheduleNextWithRandomDelay { doShop(cartId = cartId) }
    }

    private fun TaskScheduler.scheduleNextWithRandomDelay(block: () -> Unit) {
        schedule(block, Instant.now().plusSeconds(Random.nextLong(from = 3, until = 8)))
    }

    override fun run(vararg args: String) {
        triggerShopping(cartId = UUID.randomUUID())
        triggerShopping(cartId = UUID.randomUUID())
        triggerShopping(cartId = UUID.randomUUID())
    }
}
