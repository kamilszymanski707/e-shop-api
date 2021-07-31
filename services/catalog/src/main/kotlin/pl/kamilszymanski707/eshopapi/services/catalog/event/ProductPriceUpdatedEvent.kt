package pl.kamilszymanski707.eshopapi.services.catalog.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_PRICE_UPDATED_QUEUE
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.Product

class ProductPriceUpdatedEvent(
    source: Any,
    val product: Product,
) : ApplicationEvent(source)

@Component
internal class ProductPriceUpdatedEventListener(
    private val rabbitTemplate: RabbitTemplate,
) : ApplicationListener<ProductPriceUpdatedEvent> {

    private val mapper = jacksonObjectMapper()

    override fun onApplicationEvent(
        event: ProductPriceUpdatedEvent,
    ) {
        val product = event.product
        val bytea = mapper.writeValueAsBytes(product)

        rabbitTemplate.convertAndSend(PRODUCT_PRICE_UPDATED_QUEUE, bytea)
    }
}
