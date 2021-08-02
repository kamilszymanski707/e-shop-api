package pl.kamilszymanski707.eshopapi.services.basket.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.domain.Example
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.LoggerConstant.Companion.LOGGER
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_REMOVED_QUEUE
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCartItem
import pl.kamilszymanski707.eshopapi.services.basket.data.repository.ShoppingCartRepository

@Component
@RabbitListener(queues = [PRODUCT_REMOVED_QUEUE])
internal class RemoveProductListener(
    private val shoppingCartRepository: ShoppingCartRepository,
) {
    private val mapper = jacksonObjectMapper()

    @RabbitHandler(isDefault = true)
    fun handle(bytea: ByteArray) {
        val value = mapper.readValue(bytea, String::class.java)

        LOGGER.info("Message received {}", value)

        val example = Example.of(ShoppingCart.createInstance(null,
            listOf(ShoppingCartItem.createInstance(value, null, null, null))))

        val all = shoppingCartRepository.findAll(example)

        all.forEach { handleRemove(it, value) }
    }

    private fun handleRemove(
        cart: ShoppingCart,
        productId: String,
    ) {
        LOGGER.info("Cart {}", cart.toString())
        LOGGER.info("Product ID {}", productId)

        val items = arrayListOf<ShoppingCartItem>()

        for (item in cart.items)
            if (productId != item.productId)
                items.add(item)

        cart.items = items

        shoppingCartRepository.save(cart)

        LOGGER.info("Shopping cart updated {}", cart.toString())
    }
}
