package pl.kamilszymanski707.eshopapi.services.basket.resolver.mutation

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

        val itemsList = shoppingCart.items
            .map {
                ShoppingCartItemOutput(
                    it.productId!!, it.quantity!!,
                    it.price!!)
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
        val shoppingCartItemList = arrayListOf<ShoppingCartItem>()

        items.stream()
            .forEach {
                val productId = it.productId

                val searchedProduct = catalogClient.getProductById(productId)

                val existingItem = shoppingCartItemList.find { item -> item.productId.equals(productId) }

                if (existingItem != null) {
                    setExistingItem(shoppingCartItemList, existingItem, it.quantity, productId, searchedProduct.price)
                } else {
                    setNewItem(shoppingCartItemList, it.quantity, productId, searchedProduct.price)
                }
            }

        return shoppingCartItemList
    }

    private fun setExistingItem(
        shoppingCartItemList: ArrayList<ShoppingCartItem>,
        existingItem: ShoppingCartItem,
        quantity: Int,
        productId: String,
        price: BigDecimal,
    ) {
        val existingItemIndex = shoppingCartItemList.indexOf(existingItem)
        existingItem.quantity = existingItem.quantity!!.plus(quantity)

        existingItem.price = computePrice
            .apply(productId, price)
            .multiply(BigDecimal(existingItem.quantity!!))

        shoppingCartItemList[existingItemIndex] = existingItem
    }

    private fun setNewItem(
        shoppingCartItemList: ArrayList<ShoppingCartItem>,
        quantity: Int,
        productId: String,
        price: BigDecimal,
    ) {
        val updatedPrice = computePrice
            .apply(productId, price)
            .multiply(BigDecimal(quantity))

        val shoppingCartItem = ShoppingCartItem.createInstance(
            productId, quantity,
            updatedPrice)

        shoppingCartItemList.add(shoppingCartItem)
    }

    private fun getPrincipalId(): String =
        SecurityContextHolder.getContext().authentication.name
}