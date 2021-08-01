package pl.kamilszymanski707.eshopapi.core.gateway.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
internal class Resilience4JConfig {

    @Bean
    fun circuitBreakerCustomizer(): Customizer<ReactiveResilience4JCircuitBreakerFactory> =
        Customizer<ReactiveResilience4JCircuitBreakerFactory> { factory ->
            factory.configureDefault {
                Resilience4JConfigBuilder(it)
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
                    .build()
            }
        }
}