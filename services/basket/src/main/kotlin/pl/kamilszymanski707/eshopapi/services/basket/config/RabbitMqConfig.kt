package pl.kamilszymanski707.eshopapi.services.basket.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.couponAmountUpdatedQueue
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.exchangeName
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.productPriceUpdatedQueue
import pl.kamilszymanski707.eshopapi.services.basket.config.RabbitMqConfigConstants.Companion.routingKey

@Configuration
internal class RabbitMqConfig {

    @Bean
    fun appExchangeBean(): TopicExchange =
        TopicExchange(exchangeName)

    @Bean
    fun couponAmountUpdatedQueueBean(): Queue =
        Queue(couponAmountUpdatedQueue)

    @Bean
    fun productPriceUpdatedQueueBean(): Queue =
        Queue(productPriceUpdatedQueue)

    @Bean
    fun declareBindingCouponAmountUpdated(): Binding =
        BindingBuilder
            .bind(couponAmountUpdatedQueueBean())
            .to(appExchangeBean())
            .with(routingKey)

    @Bean
    fun declareBindingProductPriceUpdatedQueueBean(): Binding =
        BindingBuilder
            .bind(productPriceUpdatedQueueBean())
            .to(appExchangeBean())
            .with(routingKey)
}

class RabbitMqConfigConstants {
    companion object {
        const val exchangeName = "fanout"
        const val couponAmountUpdatedQueue = "coupon-amount-updated-queue"
        const val productPriceUpdatedQueue = "product-price-updated-queue"
        const val routingKey = "pl.kamilszymanski707.eshopapi.services"
    }
}
