package de.digitalfrontiers.axon.infrastructure.tracking

import de.digitalfrontiers.axon.infrastructure.persistence.SingletonAggregateIdRepository
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class InfrastructureCommandRouting(
    private val singletonAggregateIdRepository: SingletonAggregateIdRepository,
    private val commandGateway: CommandGateway
) {

    @CommandHandler
    fun handle(command: ProvideInformationCommand) {
        val infraStructureId =
            singletonAggregateIdRepository
                .findByIdOrNull(InfrastructureAggregate.TYPE)
                ?.aggregateIdentifier
                ?.let(UUID::fromString)
                ?: UUID.randomUUID()

        commandGateway.sendAndWait<Unit>(
            ProvideInfrastructureInformationCommand(
                id = infraStructureId,
                information = command
            )
        )
    }
}
