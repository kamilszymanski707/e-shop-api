package pl.kamilszymanski707.eshopapi.services.basket.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.couponAmountUpdatedQueue
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.productPriceUpdatedQueue

@Configuration
internal class RabbitMqConfig {

    @Bean
    fun couponAmountUpdatedQueueBean(): Queue =
        Queue(couponAmountUpdatedQueue)

    @Bean
    fun productPriceUpdatedQueueBean(): Queue =
        Queue(productPriceUpdatedQueue)
}

class RabbitMqConfigConstants {
    companion object {
        const val couponAmountUpdatedQueue = "coupon-amount-updated-queue"
        const val productPriceUpdatedQueue = "product-price-updated-queue"
    }
}
