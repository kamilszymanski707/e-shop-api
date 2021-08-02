package pl.kamilszymanski707.eshopapi.services.basket.resolver.mutation

import org.springframework.context.annotation.Scope
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.lib.utilslib.client.CatalogClient
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceNotFoundException
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCartItem
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartItemOutput
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartOutput
import java.math.BigDecimal
import java.util.function.BiFunction

@Service
@Scope("prototype")
internal class ShoppingCartMutationService(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val catalogClient: CatalogClient,
    private val computePrice: BiFunction<String, BigDecimal, BigDecimal>,
) {

    fun updateBasket(input: ShoppingCartUpdateInput): ShoppingCartOutput {
        val principalId = getPrincipalId()

        val basket = shoppingCartRepository.findById(principalId)
            .orElseThrow { ResourceNotFoundException("Basket for user with id: $principalId does not exists.") }

        val shoppingCartItemList = updatedItems(input.items)

        var shoppingCart = ShoppingCart.createInstance(
            basket.userId, shoppingCartItemList)

        shoppingCart = shoppingCartRepository.save(shoppingCart)

        val itemsList = shoppingCart.items.stream()
            .map {
                ShoppingCartItemOutput(
                    it.productId!!, it.quantity!!,
                    it.price!!, it.productName!!)
            }
            .toList()

        return ShoppingCartOutput(itemsList)
    }

    fun deleteBasket(): Boolean {
        val principalId = getPrincipalId()

        val basket = shoppingCartRepository.findById(principalId)
            .orElseThrow { ResourceNotFoundException("Basket for user with id: $principalId does not exists.") }

        shoppingCartRepository.delete(basket)

        return !shoppingCartRepository.existsById(principalId)
    }

    private fun updatedItems(items: List<ShoppingCartItemUpdateInput>): List<ShoppingCartItem> {
        val shoppingCartItemList = ArrayList<ShoppingCartItem>()

        items.forEach {
            val productId = it.productId

            val searchedProduct = catalogClient.getProductById(productId)

            val updatedPrice = computePrice.apply(productId, searchedProduct.price)

            val shoppingCartItem = ShoppingCartItem.createInstance(
                productId, it.quantity,
                updatedPrice, searchedProduct.name)

            shoppingCartItemList.add(shoppingCartItem)
        }

        return shoppingCartItemList
    }

    private fun getPrincipalId(): String =
        SecurityContextHolder.getContext().authentication.name
}