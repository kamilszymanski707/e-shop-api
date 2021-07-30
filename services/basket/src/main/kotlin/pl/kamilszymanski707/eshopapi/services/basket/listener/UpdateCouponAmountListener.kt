package pl.kamilszymanski707.eshopapi.services.basket.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.domain.Example
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.couponAmountUpdatedQueue
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCartItem
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository

@Component
@RabbitListener(queues = [couponAmountUpdatedQueue])
internal class UpdateCouponAmountListener(
    private val shoppingCartRepository: ShoppingCartRepository,
) {

    private val logger: Logger = LoggerFactory.getLogger(UpdateCouponAmountListener::class.java)
    private val mapper = jacksonObjectMapper()

    @RabbitHandler(isDefault = true)
    fun handle(bytea: ByteArray) {
        val value = mapper.readValue(bytea, Coupon::class.java)

        logger.info("Message received {}", value.toString())

        val example = Example.of(ShoppingCart.createInstance(null,
            listOf(ShoppingCartItem.createInstance(value.productId, null, null, null))))

        val all = shoppingCartRepository.findAll(example)

        all.forEach(this::handleUpdate)
    }

    private fun handleUpdate(cart: ShoppingCart) {

    }
}

internal data class Coupon(
    val id: Int,
    val description: String,
    val productId: String,
    val amount: Int,
)
