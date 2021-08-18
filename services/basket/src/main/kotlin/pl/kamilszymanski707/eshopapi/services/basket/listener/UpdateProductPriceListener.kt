package pl.kamilszymanski707.eshopapi.services.basket.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.domain.Example
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_PRICE_UPDATED_QUEUE
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCartItem
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository
import java.math.BigDecimal
import java.util.function.BiFunction

@Component
@RabbitListener(queues = [PRODUCT_PRICE_UPDATED_QUEUE])
internal class UpdateProductPriceListener(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val computePrice: BiFunction<String, BigDecimal, BigDecimal>,
) {

    private val mapper = jacksonObjectMapper()

    @RabbitHandler(isDefault = true)
    fun handle(bytea: ByteArray) {
        val productPriceUpdated = mapper.readValue(bytea, ProductPriceUpdated::class.java)

        val shoppingCartItems = listOf(ShoppingCartItem.createInstance(productPriceUpdated.id, null, null))
        val shoppingCart = ShoppingCart.createInstance(null, shoppingCartItems)

        shoppingCartRepository.findAll(Example.of(shoppingCart))
            .forEach { handleUpdate(it, productPriceUpdated) }
    }

    private fun handleUpdate(
        cart: ShoppingCart,
        productPriceUpdated: ProductPriceUpdated,
    ) {
        cart.items = cart.items
            .filter { it.productId.equals(productPriceUpdated.id) }
            .map {
                it.price = computePrice.apply(it.productId!!, productPriceUpdated.price)
                return@map it
            }

        shoppingCartRepository.save(cart)
    }
}

internal data class ProductPriceUpdated(
    val id: String,
    val price: BigDecimal,
)
