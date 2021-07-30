package pl.kamilszymanski707.eshopapi.services.basket.resolver.mutation

import javax.validation.constraints.NotNull

internal data class ShoppingCartUpdateInput(

    val items: List<ShoppingCartItemUpdateInput>,
)

internal data class ShoppingCartItemUpdateInput(

    @field:NotNull(message = "Basket item id cannot be empty.")
    val productId: String,

    @field:NotNull(message = "Basket item quantity cannot be empty.")
    val quantity: Int,
)
