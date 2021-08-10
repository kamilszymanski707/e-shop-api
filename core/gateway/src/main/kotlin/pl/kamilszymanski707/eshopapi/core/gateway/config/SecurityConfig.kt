package pl.kamilszymanski707.eshopapi.core.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
internal class SecurityConfig {

    @Value("\${route-prefix}")
    private lateinit var routePrefix: String

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .csrf().disable()
            .authorizeExchange {
                it
                    .pathMatchers(POST, "${routePrefix}catalog/**").permitAll()
                    .pathMatchers(POST, "${routePrefix}discount/**").permitAll()
                    .pathMatchers(POST, "${routePrefix}basket/**").permitAll()
                    .anyExchange().authenticated()
            }
            .oauth2Client().and()
            .build()
}
