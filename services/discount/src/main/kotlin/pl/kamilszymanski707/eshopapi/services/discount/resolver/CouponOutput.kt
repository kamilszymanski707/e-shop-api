package pl.kamilszymanski707.eshopapi.services.discount.resolver

data class CouponOutput(
    val id: Int,
    val description: String,
    val productId: String,
    val amount: Int,
)
