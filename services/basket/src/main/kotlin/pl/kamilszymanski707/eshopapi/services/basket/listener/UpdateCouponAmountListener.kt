package pl.kamilszymanski707.eshopapi.services.basket.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.domain.Example
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.LoggerConstant.Companion.LOGGER
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_AMOUNT_UPDATED_QUEUE
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCartItem
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository

@Component
@RabbitListener(queues = [COUPON_AMOUNT_UPDATED_QUEUE])
internal class UpdateCouponAmountListener(
    private val shoppingCartRepository: ShoppingCartRepository,
) {

    private val mapper = jacksonObjectMapper()

    @RabbitHandler(isDefault = true)
    fun handle(bytea: ByteArray) {
        val value = mapper.readValue(bytea, Coupon::class.java)

        LOGGER.info("Message received {}", value.toString())

        val example = Example.of(ShoppingCart.createInstance(null,
            listOf(ShoppingCartItem.createInstance(value.productId, null, null, null))))

        val all = shoppingCartRepository.findAll(example)

        all.forEach { handleUpdate(it, value) }
    }

    private fun handleUpdate(cart: ShoppingCart, coupon: Coupon) {

    }
}

internal data class Coupon(
    val id: Int,
    val description: String,
    val productId: String,
    val amount: Int,
)
