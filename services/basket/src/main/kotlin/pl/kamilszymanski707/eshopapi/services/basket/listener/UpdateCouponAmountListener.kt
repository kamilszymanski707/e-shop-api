package pl.kamilszymanski707.eshopapi.services.basket.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.domain.Example
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.client.CatalogClient
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_AMOUNT_UPDATED_QUEUE
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCartItem
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository
import java.math.BigDecimal

@Component
@RabbitListener(queues = [COUPON_AMOUNT_UPDATED_QUEUE])
internal class UpdateCouponAmountListener(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val catalogClient: CatalogClient,
) {

    private val mapper = jacksonObjectMapper()

    @RabbitHandler(isDefault = true)
    fun handle(bytea: ByteArray) {
        val couponAmountUpdated = mapper.readValue(bytea, CouponAmountUpdated::class.java)

        val shoppingCartItems = listOf(ShoppingCartItem.createInstance(couponAmountUpdated.productId, null, null))
        val shoppingCart = ShoppingCart.createInstance(null, shoppingCartItems)

        shoppingCartRepository.findAll(Example.of(shoppingCart))
            .forEach { handleUpdate(it, couponAmountUpdated) }
    }

    private fun handleUpdate(
        cart: ShoppingCart,
        couponAmountUpdated: CouponAmountUpdated,
    ) {
        cart.items = cart.items
            .filter { it.productId.equals(couponAmountUpdated.productId) }
            .map {
                val product = catalogClient.getProductById(it.productId!!)
                val amount = couponAmountUpdated.amount

                it.price =
                    if (amount == 0) product.price
                    else product.price.multiply(BigDecimal(amount.toDouble() / 100))

                return@map it
            }

        shoppingCartRepository.save(cart)
    }
}

internal data class CouponAmountUpdated(
    val productId: String,
    val amount: Int,
)
