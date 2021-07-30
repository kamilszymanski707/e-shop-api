package pl.kamilszymanski707.eshopapi.services.basket.resolver.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartOutput
import javax.validation.Valid

@Component
@Validated
@PreAuthorize("hasRole('user')")
internal class BasketMutationResolver(
    private val shoppingCartMutationService: ShoppingCartMutationService,
) : GraphQLMutationResolver {

    fun updateBasket(@Valid input: ShoppingCartUpdateInput): ShoppingCartOutput =
        shoppingCartMutationService.updateBasket(input)

    fun deleteBasket(): Boolean =
        shoppingCartMutationService.deleteBasket()
}