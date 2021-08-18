package pl.kamilszymanski707.eshopapi.services.discount.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.LoggerConstant.Companion.LOGGER
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_REMOVED_QUEUE

class CouponRemovedEvent(
    source: Any,
    val productId: String,
) : ApplicationEvent(source)

@Component
internal class CouponRemovedEventListener(
    private val rabbitTemplate: RabbitTemplate,
) : ApplicationListener<CouponRemovedEvent> {

    val mapper = jacksonObjectMapper()

    override fun onApplicationEvent(
        event: CouponRemovedEvent,
    ) {
        val productId = event.productId
        val bytea = mapper.writeValueAsBytes(productId)

        LOGGER.info(
            "Sending: {} to RabbitMQ Queue: {}",
            productId,
            COUPON_REMOVED_QUEUE)

        rabbitTemplate.convertAndSend(COUPON_REMOVED_QUEUE, bytea)
    }
}
