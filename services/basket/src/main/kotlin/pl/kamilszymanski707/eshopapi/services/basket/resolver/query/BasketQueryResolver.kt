package pl.kamilszymanski707.eshopapi.services.basket.resolver.query

import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartOutput

@Component
@PreAuthorize("hasRole('user')")
internal class BasketQueryResolver(
    private val shoppingCartQueryService: ShoppingCartQueryService,
) : GraphQLQueryResolver {

    fun getBasket(): ShoppingCartOutput =
        shoppingCartQueryService.getBasket()
}