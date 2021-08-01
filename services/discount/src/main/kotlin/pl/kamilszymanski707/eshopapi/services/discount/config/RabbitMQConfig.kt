package pl.kamilszymanski707.eshopapi.services.discount.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_AMOUNT_UPDATED_QUEUE
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_REMOVED_QUEUE

@Configuration
internal class RabbitMQConfig {

    @Bean
    fun couponAmountUpdatedQueueBean(): Queue =
        Queue(COUPON_AMOUNT_UPDATED_QUEUE)

    @Bean
    fun couponRemovedQueueBean(): Queue =
        Queue(COUPON_REMOVED_QUEUE)
}
