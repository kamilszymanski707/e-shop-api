package pl.kamilszymanski707.eshopapi.services.discount.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.LoggerConstant.Companion.LOGGER
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_AMOUNT_UPDATED_QUEUE

class CouponAmountUpdatedEvent(
    source: Any,
    val couponAmountUpdated: CouponAmountUpdated,
) : ApplicationEvent(source)

data class CouponAmountUpdated(
    val productId: String,
    val amount: Int,
)

@Component
internal class CouponAmountUpdatedEventListener(
    private val rabbitTemplate: RabbitTemplate,
) : ApplicationListener<CouponAmountUpdatedEvent> {

    val mapper = jacksonObjectMapper()

    override fun onApplicationEvent(
        event: CouponAmountUpdatedEvent,
    ) {
        val couponAmountUpdated = event.couponAmountUpdated
        val bytea = mapper.writeValueAsBytes(couponAmountUpdated)

        LOGGER.info(
            "Sending: {} to RabbitMQ Queue: {}",
            couponAmountUpdated,
            COUPON_AMOUNT_UPDATED_QUEUE)

        rabbitTemplate.convertAndSend(COUPON_AMOUNT_UPDATED_QUEUE, bytea)
    }
}
