package pl.kamilszymanski707.eshopapi.services.basket.resolver.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartOutput

@Component
@PreAuthorize("hasRole('user')")
internal class BasketMutationResolver(
    private val shoppingCartMutationService: ShoppingCartMutationService,
) : GraphQLMutationResolver {

    fun updateBasket(input: ShoppingCartUpdateInput): ShoppingCartOutput =
        shoppingCartMutationService.updateBasket(input)

    fun deleteBasket(): Boolean =
        shoppingCartMutationService.deleteBasket()
}