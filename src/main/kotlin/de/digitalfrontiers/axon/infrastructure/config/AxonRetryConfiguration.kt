package de.digitalfrontiers.axon.infrastructure.config

import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.commandhandling.gateway.ExponentialBackOffIntervalRetryScheduler
import org.axonframework.modelling.command.ConcurrencyException
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

@Configuration
class AxonRetryConfiguration {

    /**
     * Enables command retry for all exceptions not explicitly derived from
     * [org.axonframework.common.AxonNonTransientException], i.e. unresolvable errors.
     *
     * The main cause for retries is parallel command execution in distributed environments,
     * resulting in [ConcurrencyException]s due to violation of unique constraints.
     * This can be mitigated by retrying such commands using exponential back-off.
     * If all 3 retries fail, the last exception is propagated to the caller.
     */
    @Bean
    @ConditionalOnProperty(prefix = "axon.command.retry", name = ["enabled"], havingValue = "true", matchIfMissing = true)
    fun commandGateway(commandBus: CommandBus): CommandGateway =
        DefaultCommandGateway
            .builder()
            .commandBus(commandBus)
            .retryScheduler(
                ExponentialBackOffIntervalRetryScheduler
                    .builder()
                    .retryExecutor(Executors.newScheduledThreadPool(4))
                    // max number of retries in addition to the first failing command execution
                    .maxRetryCount(2)
                    .backoffFactor(100)
                    .build()
            )
            .build()
}
