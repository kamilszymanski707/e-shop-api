package pl.kamilszymanski707.eshopapi.services.basket.data.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.math.BigDecimal

@RedisHash("shopping_cart")
class ShoppingCart {

    @field:Id
    @field:Indexed
    var userId: String? = null

    var items: List<ShoppingCartItem> = ArrayList()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShoppingCart

        if (userId != other.userId) return false
        if (items != other.items) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId?.hashCode() ?: 0
        result = 31 * result + items.hashCode()
        return result
    }

    override fun toString(): String {
        return "ShoppingCart(userId=$userId, items=$items)"
    }

    companion object {

        fun createInstance(
            userId: String?,
            items: List<ShoppingCartItem>?,
        ): ShoppingCart {

            val result = ShoppingCart()
            result.userId = userId
            result.items = items ?: ArrayList()

            return result
        }
    }
}

class ShoppingCartItem {

    var productId: String? = null
    var quantity: Int? = null
    var price: BigDecimal? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShoppingCartItem

        if (productId != other.productId) return false
        if (quantity != other.quantity) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = productId?.hashCode() ?: 0
        result = 31 * result + (quantity ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ShoppingCartItem(productId=$productId, quantity=$quantity, price=$price)"
    }

    companion object {

        fun createInstance(
            productId: String?, quantity: Int?,
            price: BigDecimal?,
        ): ShoppingCartItem {

            val result = ShoppingCartItem()
            result.productId = productId
            result.quantity = quantity
            result.price = price
            return result
        }
    }
}
