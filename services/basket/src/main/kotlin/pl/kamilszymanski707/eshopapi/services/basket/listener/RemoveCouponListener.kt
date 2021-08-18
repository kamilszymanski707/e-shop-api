package pl.kamilszymanski707.eshopapi.services.basket.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.domain.Example
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.client.CatalogClient
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_REMOVED_QUEUE
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCartItem
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository
import java.math.BigDecimal

@Component
@RabbitListener(queues = [COUPON_REMOVED_QUEUE])
internal class RemoveCouponListener(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val catalogClient: CatalogClient,
) {

    private val mapper = jacksonObjectMapper()

    @RabbitHandler(isDefault = true)
    fun handle(bytea: ByteArray) {
        val productId = mapper.readValue(bytea, String::class.java)

        val shoppingCartItems = listOf(ShoppingCartItem.createInstance(productId, null, null))
        val shoppingCart = ShoppingCart.createInstance(null, shoppingCartItems)

        shoppingCartRepository.findAll(Example.of(shoppingCart))
            .forEach { handleRemove(it, productId) }
    }

    private fun handleRemove(
        cart: ShoppingCart,
        productId: String,
    ) {
        cart.items = cart.items
            .filter { it.productId.equals(productId) }
            .map {
                val product = catalogClient.getProductById(productId)

                it.price = product.price
                    .multiply(BigDecimal(it.quantity!!))

                return@map it
            }

        shoppingCartRepository.save(cart)
    }
}