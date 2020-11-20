package de.digitalfrontiers.axon.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class AxonInfrastructureApplication

fun main(args: Array<String>) {
    runApplication<AxonInfrastructureApplication>(*args)
}
