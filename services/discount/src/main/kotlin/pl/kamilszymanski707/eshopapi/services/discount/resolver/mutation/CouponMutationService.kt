package pl.kamilszymanski707.eshopapi.services.discount.resolver.mutation

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.services.discount.client.CatalogClient
import pl.kamilszymanski707.eshopapi.services.discount.data.domain.Coupon
import pl.kamilszymanski707.eshopapi.services.discount.data.repository.CouponRepository
import pl.kamilszymanski707.eshopapi.services.discount.event.CouponAmountUpdatedEvent
import pl.kamilszymanski707.eshopapi.services.discount.exception.ResourceFoundException
import pl.kamilszymanski707.eshopapi.services.discount.exception.ResourceNotFoundException
import pl.kamilszymanski707.eshopapi.services.discount.resolver.CouponOutput

@Service
internal class CouponMutationService(
    private val couponRepository: CouponRepository,
    private val catalogClient: CatalogClient,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun createCoupon(input: CouponCreateInput): CouponOutput {
        val optionalCoupon = couponRepository.findByProductId(input.productId)
        if (optionalCoupon.isPresent)
            throw ResourceFoundException("Coupon for product with id: ${input.productId} already exist.")

        return saveOrUpdate(null, input.productId, input.description, input.amount)
    }

    fun updateCoupon(input: CouponUpdateInput): CouponOutput {
        val coupon = couponRepository.findByProductId(input.productId)
            .orElseThrow { ResourceNotFoundException("Coupon for product with id: ${input.productId} does not exist.") }

        return saveOrUpdate(coupon.id, input.productId, input.description, input.amount)
    }

    fun deleteCoupon(id: Int): Boolean {
        val coupon = couponRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Product with id: $id not found.") }

        couponRepository.delete(coupon)
        return !couponRepository.existsById(id)
    }

    private fun saveOrUpdate(
        couponId: Int?,
        productId: String,
        description: String,
        amount: Int,
    ): CouponOutput {
        val productOutput = catalogClient.getProductsByQuery(productId, null, null)
            ?: throw ResourceNotFoundException("Product with id: $productId does not exist.")

        val products = productOutput.data?.getProductsByQuery
        if (products == null || products.isEmpty() || products.size > 1)
            throw ResourceNotFoundException("Product with id: $productId does not exist.")

        var coupon = Coupon(couponId, description, products[0].id!!, amount)
        coupon = couponRepository.save(coupon)

        applicationEventPublisher.publishEvent(CouponAmountUpdatedEvent(this, coupon))
        return CouponOutput(coupon.id!!, coupon.description, coupon.productId, coupon.amount)
    }
}
