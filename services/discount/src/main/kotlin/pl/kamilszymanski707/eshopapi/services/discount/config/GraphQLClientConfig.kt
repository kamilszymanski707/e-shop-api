package pl.kamilszymanski707.eshopapi.services.discount.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import pl.kamilszymanski707.eshopapi.lib.utilslib.client.CatalogClient

@Configuration
class GraphQLClientConfig(
    private val restTemplate: RestTemplate,
) {

    @Bean
    fun catalogClientBean(): CatalogClient =
        CatalogClient.catalogClient(restTemplate)
}