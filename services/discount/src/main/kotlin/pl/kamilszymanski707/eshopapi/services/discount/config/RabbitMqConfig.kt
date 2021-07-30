package pl.kamilszymanski707.eshopapi.services.discount.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.services.discount.config.RabbitMqConfigConstants.Companion.couponAmountUpdatedQueue

@Configuration
internal class RabbitMqConfig {

    @Bean
    fun couponAmountUpdatedQueueBean(): Queue =
        Queue(couponAmountUpdatedQueue)
}

class RabbitMqConfigConstants {
    companion object {
        const val couponAmountUpdatedQueue = "coupon-amount-updated-queue"
    }
}
