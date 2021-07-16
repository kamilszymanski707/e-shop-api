package pl.kamilszymanski707.eshopapi.services.catalog.resolver.query

import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.catalog.resolver.ProductOutput

@Component
internal class CatalogQueryResolver(
    private val productQueryService: ProductQueryService,
) : GraphQLQueryResolver {

    fun getProductsByQuery(input: ProductQueryInput): List<ProductOutput> =
        productQueryService.getProductsByQuery(input)
}