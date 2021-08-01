package pl.kamilszymanski707.eshopapi.services.discount.resolver.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import pl.kamilszymanski707.eshopapi.services.discount.resolver.CouponOutput
import javax.validation.Valid

@Component
@Validated
@PreAuthorize("hasRole('admin')")
internal class DiscountMutationResolver(
    private val couponMutationService: CouponMutationService,
) : GraphQLMutationResolver {

    fun createCoupon(@Valid input: CouponCreateInput): CouponOutput =
        couponMutationService.createCoupon(input)

    fun updateCoupon(@Valid input: CouponUpdateInput): CouponOutput =
        couponMutationService.updateCoupon(input)

    fun deleteCoupon(productId: String): Boolean =
        couponMutationService.deleteCoupon(productId)
}
