package pl.kamilszymanski707.eshopapi.core.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux

@Configuration
internal class RouterConfig {

    @Value("\${route-prefix}")
    private lateinit var routePrefix: String

    @Bean
    fun router(): RouterFunction<ServerResponse> =
        route(POST("${routePrefix}fallback")) {
            ok().body(Flux.just("{}"), String::class.java)
        }
}