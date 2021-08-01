package pl.kamilszymanski707.eshopapi.services.basket.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_AMOUNT_UPDATED_QUEUE
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.COUPON_REMOVED_QUEUE
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_PRICE_UPDATED_QUEUE
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_REMOVED_QUEUE

@Configuration
internal class RabbitMQConfig {

    @Bean
    fun couponAmountUpdatedQueueBean(): Queue =
        Queue(COUPON_AMOUNT_UPDATED_QUEUE)

    @Bean
    fun couponRemovedQueueBean(): Queue =
        Queue(COUPON_REMOVED_QUEUE)

    @Bean
    fun productPriceUpdatedQueueBean(): Queue =
        Queue(PRODUCT_PRICE_UPDATED_QUEUE)

    @Bean
    fun productRemovedQueueBean(): Queue =
        Queue(PRODUCT_REMOVED_QUEUE)
}

