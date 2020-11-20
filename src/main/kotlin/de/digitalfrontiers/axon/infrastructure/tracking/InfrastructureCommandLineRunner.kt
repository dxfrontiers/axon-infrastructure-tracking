package de.digitalfrontiers.axon.infrastructure.tracking

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.info.GitProperties
import org.springframework.boot.system.JavaVersion
import org.springframework.stereotype.Component

@Component
class InfrastructureCommandLineRunner(
    private val gitProperties: GitProperties,
    private val buildProperties: BuildProperties,
    private val commandGateway: CommandGateway
) : CommandLineRunner {

    override fun run(vararg args: String) {
        commandGateway.sendAndWait<Unit>(
            ProvideInformationCommand(
                commitId = gitProperties.shortCommitId,
                softwareVersion = buildProperties.version,
                javaVersion = JavaVersion.getJavaVersion()
            )
        )
    }
}
