package de.digitalfrontiers.axon.infrastructure.tracking

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.springframework.boot.system.JavaVersion
import java.util.*

data class ProvideInformationCommand(
    val commitId: String,
    val softwareVersion: String,
    val javaVersion: JavaVersion
)

data class ProvideInfrastructureInformationCommand(
    @TargetAggregateIdentifier
    val id: UUID,
    val information: ProvideInformationCommand
)
