package pl.kamilszymanski707.eshopapi.services.discount.resolver.query

import pl.kamilszymanski707.eshopapi.services.discount.data.QueryableCoupon

internal data class CouponQueryInput(
    val id: Int?,
    val description: String?,
    val productId: String?,
) : QueryableCoupon