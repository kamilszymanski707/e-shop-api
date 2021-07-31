package pl.kamilszymanski707.eshopapi.services.catalog.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_PRICE_UPDATED_QUEUE

@Configuration
internal class RabbitMQConfig {

    @Bean
    fun productPriceUpdatedQueueBean(): Queue =
        Queue(PRODUCT_PRICE_UPDATED_QUEUE)
}
