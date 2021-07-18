package pl.kamilszymanski707.eshopapi.services.discount.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CouponUpdateDto(

    @field:NotNull(message = "Coupon id cannot be empty.")
    val id: Int,

    @field:NotNull(
        message = "Coupon description cannot be empty.")
    @field:Size(
        min = 3,
        max = 100,
        message = "Coupon description must be between 3 and 20 characters long.")
    val description: String,

    @field:NotNull(message = "Coupon product id cannot be empty.")
    val productId: String,

    @field:Size(min = 0, max = 100)
    @field:NotNull(message = "Coupon amount cannot be empty.")
    val amount: Int,
)
