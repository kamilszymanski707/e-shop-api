package pl.kamilszymanski707.eshopapi.services.basket.resolver

import java.math.BigDecimal

data class ShoppingCartOutput(
    val items: List<ShoppingCartItemOutput>,
)

data class ShoppingCartItemOutput(
    val productId: String,
    val quantity: Int,
    val price: BigDecimal,
    val productName: String,
)
