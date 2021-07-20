package pl.kamilszymanski707.eshopapi.services.discount.resolver.query

import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.services.discount.resolver.CouponOutput

@Component
internal class DiscountQueryResolver(
    private val couponQueryService: CouponQueryService,
) : GraphQLQueryResolver {

    fun getCouponsByQuery(input: CouponQueryInput?): List<CouponOutput> =
        couponQueryService.getCouponsByQuery(input)
}
