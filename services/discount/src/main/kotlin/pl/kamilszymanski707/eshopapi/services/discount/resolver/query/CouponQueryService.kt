package pl.kamilszymanski707.eshopapi.services.discount.resolver.query

import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.services.discount.data.dao.CouponDao
import pl.kamilszymanski707.eshopapi.services.discount.resolver.CouponOutput

@Service
internal class CouponQueryService(
    private val couponDao: CouponDao,
) {

    fun getCouponsByQuery(input: CouponQueryInput?): List<CouponOutput> =
        couponDao.findByQuery(input)
            .map { CouponOutput(it.id!!, it.description, it.productId, it.amount) }
}
