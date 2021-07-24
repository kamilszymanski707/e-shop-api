package pl.kamilszymanski707.eshopapi.services.basket.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.couponAmountUpdatedQueue
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository

@Component
internal class UpdateCouponAmountListener(
    private val shoppingCartRepository: ShoppingCartRepository,
) {

    private val logger: Logger = LoggerFactory.getLogger(UpdateCouponAmountListener::class.java)
    private val mapper = jacksonObjectMapper()

    @RabbitListener(
        queues = [couponAmountUpdatedQueue])
    fun handle(message: Message) {
        val body = message.body
        val value = mapper.readValue(body, Coupon::class.java)

        logger.info("Message received {}", value.toString())
    }
}

internal data class Coupon(
    val id: Int,
    val description: String,
    val productId: String,
    val amount: Int,
)
