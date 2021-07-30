package pl.kamilszymanski707.eshopapi.services.basket.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.productPriceUpdatedQueue
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository
import java.math.BigDecimal

@Component
@RabbitListener(queues = [productPriceUpdatedQueue])
internal class UpdateProductPriceListener(
    private val shoppingCartRepository: ShoppingCartRepository,
) {

    private val logger: Logger = LoggerFactory.getLogger(UpdateProductPriceListener::class.java)
    private val mapper = jacksonObjectMapper()

    @RabbitHandler(isDefault = true)
    fun handle(bytea: ByteArray) {
        val value = mapper.readValue(bytea, Product::class.java)

        logger.info("Message received {}", value.toString())
    }
}

internal data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: BigDecimal,
)
