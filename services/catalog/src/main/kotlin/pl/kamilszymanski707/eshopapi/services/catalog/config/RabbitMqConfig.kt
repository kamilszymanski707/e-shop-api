package pl.kamilszymanski707.eshopapi.services.catalog.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.services.catalog.config.RabbitMqConfigConstants.Companion.exchangeName
import pl.kamilszymanski707.eshopapi.services.catalog.config.RabbitMqConfigConstants.Companion.productPriceUpdatedQueue
import pl.kamilszymanski707.eshopapi.services.catalog.config.RabbitMqConfigConstants.Companion.routingKey

@Configuration
internal class RabbitMqConfig {

    @Bean
    fun appExchangeBean(): TopicExchange =
        TopicExchange(exchangeName)

    @Bean
    fun productPriceUpdatedQueueBean(): Queue =
        Queue(productPriceUpdatedQueue)

    @Bean
    fun declareBindingGeneric(): Binding =
        BindingBuilder
            .bind(productPriceUpdatedQueueBean())
            .to(appExchangeBean())
            .with(routingKey)
}

class RabbitMqConfigConstants {
    companion object {
        const val exchangeName = "fanout"
        const val productPriceUpdatedQueue = "product-price-updated-queue"
        const val routingKey = "pl.kamilszymanski707.eshopapi.services"
    }
}
