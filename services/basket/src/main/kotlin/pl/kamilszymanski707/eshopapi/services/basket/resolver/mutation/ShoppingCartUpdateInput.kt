package pl.kamilszymanski707.eshopapi.services.basket.resolver.mutation

internal data class ShoppingCartUpdateInput(
    val items: List<ShoppingCartItemUpdateInput>,
)

internal data class ShoppingCartItemUpdateInput(
    val productId: String,
    val quantity: Int,
)
