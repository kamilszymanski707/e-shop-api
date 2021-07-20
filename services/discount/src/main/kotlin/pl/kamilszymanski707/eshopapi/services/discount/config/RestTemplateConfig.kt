package pl.kamilszymanski707.eshopapi.services.discount.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
internal class RestTemplateConfig {

    @Bean
    @LoadBalanced
    fun restTemplateBean(): RestTemplate {
        return RestTemplate()
    }
}