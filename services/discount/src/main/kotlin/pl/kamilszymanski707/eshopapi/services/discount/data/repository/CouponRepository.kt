package pl.kamilszymanski707.eshopapi.services.discount.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import pl.kamilszymanski707.eshopapi.services.discount.data.domain.Coupon
import java.util.*

interface CouponRepository : JpaRepository<Coupon, Int> {

    fun findByProductId(productId: String): Optional<Coupon>
}