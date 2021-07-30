package pl.kamilszymanski707.eshopapi.services.catalog.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.services.catalog.config.RabbitMqConfigConstants.Companion.productPriceUpdatedQueue

@Configuration
internal class RabbitMqConfig {

    @Bean
    fun productPriceUpdatedQueueBean(): Queue =
        Queue(productPriceUpdatedQueue)
}

class RabbitMqConfigConstants {
    companion object {
        const val productPriceUpdatedQueue = "product-price-updated-queue"
    }
}
