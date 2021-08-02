package pl.kamilszymanski707.eshopapi.services.basket.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import pl.kamilszymanski707.eshopapi.lib.utilslib.client.CatalogClient
import pl.kamilszymanski707.eshopapi.lib.utilslib.client.DiscountClient

@Configuration
class GraphQLClientConfig(
    private val restTemplate: RestTemplate,
) {

    @Bean
    fun catalogClientBean(): CatalogClient =
        CatalogClient.catalogClient(restTemplate)

    @Bean
    fun discountClientBean(): DiscountClient =
        DiscountClient.discountClient(restTemplate)
}