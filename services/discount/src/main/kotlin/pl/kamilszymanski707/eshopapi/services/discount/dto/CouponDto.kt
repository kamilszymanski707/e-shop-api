package pl.kamilszymanski707.eshopapi.services.discount.dto

data class CouponDto(
    val id: Int,
    val description: String,
    val productId: String,
    val amount: Int,
)
