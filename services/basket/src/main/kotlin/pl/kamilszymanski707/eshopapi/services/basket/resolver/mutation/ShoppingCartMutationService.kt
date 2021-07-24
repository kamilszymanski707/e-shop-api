package pl.kamilszymanski707.eshopapi.services.basket.resolver.mutation

import org.springframework.context.annotation.Scope
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.services.basket.client.CatalogClient
import pl.kamilszymanski707.eshopapi.services.basket.client.DiscountClient
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCartItem
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository
import pl.kamilszymanski707.eshopapi.services.basket.exception.ResourceNotFoundException
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartItemOutput
import pl.kamilszymanski707.eshopapi.services.basket.resolver.ShoppingCartOutput
import java.math.BigDecimal

@Service
@Scope("prototype")
internal class ShoppingCartMutationService(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val catalogClient: CatalogClient,
    private val discountClient: DiscountClient,
) {

    fun updateBasket(input: ShoppingCartUpdateInput): ShoppingCartOutput {
        val principalId = getPrincipalId()

        val basket = shoppingCartRepository.findById(principalId)
            .orElseThrow { ResourceNotFoundException("Basket for user with id: $principalId does not exists.") }

        val shoppingCartItemList = updatedItems(input.items)

        var shoppingCart = ShoppingCart(
            basket.userId, shoppingCartItemList)

        shoppingCart = shoppingCartRepository.save(shoppingCart)

        val itemsList = shoppingCart.items.stream()
            .map {
                ShoppingCartItemOutput(
                    it.productId, it.quantity,
                    it.price, it.productName)
            }
            .toList()

        return ShoppingCartOutput(itemsList)
    }

    private fun updatedItems(items: List<ShoppingCartItemUpdateInput>): List<ShoppingCartItem> {
        val shoppingCartItemList = ArrayList<ShoppingCartItem>()

        items.forEach {
            val productId = it.productId

            val productOutput = catalogClient.getProductsByQuery(productId, null, null)
                ?: throw ResourceNotFoundException("Product with id: $productId does not exists.")

            val products = productOutput.data?.getProductsByQuery
            if (products == null || products.isEmpty() || products.size > 1)
                throw ResourceNotFoundException("Product with id: $productId does not exists.")

            val searchedProduct = products[0]

            val updatedPrice = updatedPrice(productId, searchedProduct.price)

            val shoppingCartItem = ShoppingCartItem(
                productId, it.quantity,
                updatedPrice, searchedProduct.name)

            shoppingCartItemList.add(shoppingCartItem)
        }

        return shoppingCartItemList
    }

    private fun updatedPrice(productId: String, price: BigDecimal): BigDecimal {
        val discountOutput = discountClient.getCouponsByQuery(
            null, null, productId)

        if (discountOutput != null) {
            val coupon = discountOutput.data?.getCouponsByQuery?.get(0)
            if (coupon != null)
                return price.multiply(BigDecimal(coupon.amount.toDouble() / 100))
        }

        return price
    }

    fun deleteBasket(): Boolean {
        val principalId = getPrincipalId()

        val basket = shoppingCartRepository.findById(principalId)
            .orElseThrow { ResourceNotFoundException("Basket for user with id: $principalId does not exists.") }

        shoppingCartRepository.delete(basket)
        return !shoppingCartRepository.existsById(principalId)
    }

    fun getPrincipalId(): String =
        SecurityContextHolder.getContext().authentication.name
}