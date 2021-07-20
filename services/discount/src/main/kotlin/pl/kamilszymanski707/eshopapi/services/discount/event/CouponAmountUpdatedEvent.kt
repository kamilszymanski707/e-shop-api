package pl.kamilszymanski707.eshopapi.services.discount.event

import org.springframework.context.ApplicationEvent
import pl.kamilszymanski707.eshopapi.services.discount.data.domain.Coupon

class CouponAmountUpdatedEvent(
    source: Any,
    val coupon: Coupon,
) : ApplicationEvent(source)
