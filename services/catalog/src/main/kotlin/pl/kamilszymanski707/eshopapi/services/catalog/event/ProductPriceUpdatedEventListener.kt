package pl.kamilszymanski707.eshopapi.services.catalog.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.catalog.config.RabbitMqConfigConstants.Companion.exchangeName
import pl.kamilszymanski707.eshopapi.services.catalog.config.RabbitMqConfigConstants.Companion.routingKey

@Component
internal class ProductPriceUpdatedEventListener(
    private val rabbitTemplate: RabbitTemplate,
) : ApplicationListener<ProductPriceUpdatedEvent> {

    val mapper = jacksonObjectMapper()

    override fun onApplicationEvent(
        event: ProductPriceUpdatedEvent,
    ) {
        val product = event.product
        val bytea = mapper.writeValueAsBytes(product)
        val message = Message(bytea)

        rabbitTemplate.convertAndSend(
            exchangeName,
            routingKey,
            message)
    }
}