package pl.kamilszymanski707.eshopapi.services.discount.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.discount.config.RabbitMqConfigConstants.Companion.exchangeName
import pl.kamilszymanski707.eshopapi.services.discount.config.RabbitMqConfigConstants.Companion.routingKey

@Component
internal class CouponAmountUpdatedEventListener(
    private val rabbitTemplate: RabbitTemplate,
) : ApplicationListener<CouponAmountUpdatedEvent> {

    val mapper = jacksonObjectMapper()

    override fun onApplicationEvent(
        event: CouponAmountUpdatedEvent,
    ) {
        val coupon = event.coupon
        val bytea = mapper.writeValueAsBytes(coupon)
        val message = Message(bytea)

        rabbitTemplate.convertAndSend(
            exchangeName,
            routingKey,
            message)
    }
}