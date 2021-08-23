package pl.kamilszymanski707.eshopapi.services.catalog.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.LoggerConstant.Companion.log
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_REMOVED_QUEUE

class ProductRemovedEvent(
    source: Any,
    val productId: String,
) : ApplicationEvent(source)

@Component
internal class ProductRemovedEventListener(
    private val rabbitTemplate: RabbitTemplate,
) : ApplicationListener<ProductRemovedEvent> {

    private val mapper = jacksonObjectMapper()

    override fun onApplicationEvent(
        event: ProductRemovedEvent,
    ) {
        val productId = event.productId
        val bytea = mapper.writeValueAsBytes(productId)

        log.info(
            "Sending: {} to RabbitMQ Queue: {}",
            productId,
            PRODUCT_REMOVED_QUEUE)

        rabbitTemplate.convertAndSend(PRODUCT_REMOVED_QUEUE, bytea)
    }
}
