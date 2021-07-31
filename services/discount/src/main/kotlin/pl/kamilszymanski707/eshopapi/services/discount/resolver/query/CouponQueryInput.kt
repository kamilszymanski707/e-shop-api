package pl.kamilszymanski707.eshopapi.services.discount.resolver.query

import pl.kamilszymanski707.eshopapi.services.discount.data.QueryableCoupon

internal data class CouponQueryInput(

    override var id: Int?,
    override var description: String?,
    override var productId: String?,
) : QueryableCoupon