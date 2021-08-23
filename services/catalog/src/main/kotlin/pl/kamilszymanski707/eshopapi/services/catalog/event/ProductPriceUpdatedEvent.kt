package pl.kamilszymanski707.eshopapi.services.catalog.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.LoggerConstant.Companion.log
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_PRICE_UPDATED_QUEUE
import java.math.BigDecimal

class ProductPriceUpdatedEvent(
    source: Any,
    val productPriceUpdated: ProductPriceUpdated,
) : ApplicationEvent(source)

data class ProductPriceUpdated(
    val id: String,
    val price: BigDecimal,
)

@Component
internal class ProductPriceUpdatedEventListener(
    private val rabbitTemplate: RabbitTemplate,
) : ApplicationListener<ProductPriceUpdatedEvent> {

    private val mapper = jacksonObjectMapper()

    override fun onApplicationEvent(
        event: ProductPriceUpdatedEvent,
    ) {
        val productPriceUpdated = event.productPriceUpdated
        val bytea = mapper.writeValueAsBytes(productPriceUpdated)

        log.info(
            "Sending: {} to RabbitMQ Queue: {}",
            productPriceUpdated,
            PRODUCT_PRICE_UPDATED_QUEUE)

        rabbitTemplate.convertAndSend(PRODUCT_PRICE_UPDATED_QUEUE, bytea)
    }
}
