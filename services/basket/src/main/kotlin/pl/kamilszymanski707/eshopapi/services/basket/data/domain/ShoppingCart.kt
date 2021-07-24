package pl.kamilszymanski707.eshopapi.services.basket.data.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.math.BigDecimal

@RedisHash("shopping_cart")
data class ShoppingCart(

    @Id
    @Indexed
    val userId: String,

    val items: List<ShoppingCartItem>,
)

@RedisHash("shopping_cart_item")
data class ShoppingCartItem(

    @Id
    @Indexed
    val productId: String,

    val quantity: Int,
    val price: BigDecimal,
    val productName: String,
)
