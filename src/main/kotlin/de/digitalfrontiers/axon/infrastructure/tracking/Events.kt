package de.digitalfrontiers.axon.infrastructure.tracking

import org.axonframework.serialization.Revision
import org.springframework.boot.system.JavaVersion
import java.util.*

@Revision("1")
data class InfrastructureCreatedEvent(
    val id: UUID
)

@Revision("1")
data class SoftwareVersionDeployedEvent(
    val commitId: String,
    val version: String
)

@Revision("1")
data class JavaVersionDetectedEvent(
    val version: JavaVersion
)
