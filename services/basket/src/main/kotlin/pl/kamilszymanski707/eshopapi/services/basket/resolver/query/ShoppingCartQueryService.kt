package pl.kamilszymanski707.eshopapi.services.basket.resolver.query

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartItemOutput
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartOutput

@Service
internal class ShoppingCartQueryService(
    private val shoppingCartRepository: ShoppingCartRepository,
) {

    fun getBasket(): ShoppingCartOutput {
        val principalId = getPrincipalId()

        var basket = shoppingCartRepository.findById(principalId)
            .orElse(null)

        if (basket == null) {
            basket = ShoppingCart.createInstance(principalId, listOf())
            basket = shoppingCartRepository.save(basket)
        }

        val items = basket.items
            .map {
                ShoppingCartItemOutput(
                    it.productId!!, it.quantity!!,
                    it.price!!
                )
            }
            .toList()

        return ShoppingCartOutput(items)
    }

    private fun getPrincipalId(): String =
        SecurityContextHolder.getContext().authentication.name
}
