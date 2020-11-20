package de.digitalfrontiers.axon.infrastructure.tracking

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.boot.system.JavaVersion
import java.util.*

@Aggregate(type = InfrastructureAggregate.TYPE)
class InfrastructureAggregate {

    @AggregateIdentifier
    var id: UUID? = null

    var currentCommitId: String? = null
    var currentVersion: String? = null
    var currentJavaVersion: JavaVersion? = null

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    fun handle(command: ProvideInfrastructureInformationCommand) {
        if (id == null) {
            AggregateLifecycle.apply(
                InfrastructureCreatedEvent(id = command.id)
            )
        }

        with(command.information) {
            if (currentCommitId != commitId || currentVersion != softwareVersion) {
                AggregateLifecycle.apply(
                    SoftwareVersionDeployedEvent(
                        commitId = commitId,
                        version = softwareVersion
                    )
                )
            }

            if (currentJavaVersion != javaVersion) {
                AggregateLifecycle.apply(
                    JavaVersionDetectedEvent(
                        version = javaVersion
                    )
                )
            }
        }
    }

    @EventSourcingHandler
    fun on(event: InfrastructureCreatedEvent) {
        id = event.id
    }

    @EventSourcingHandler
    fun on(event: SoftwareVersionDeployedEvent) {
        currentCommitId = event.commitId
        currentVersion = event.version
    }

    @EventSourcingHandler
    fun on(event: JavaVersionDetectedEvent) {
        currentJavaVersion = event.version
    }

    companion object {
        const val TYPE = "Infrastructure"
    }
}
