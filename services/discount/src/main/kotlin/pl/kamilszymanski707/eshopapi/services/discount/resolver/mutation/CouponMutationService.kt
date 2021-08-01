package pl.kamilszymanski707.eshopapi.services.discount.resolver.mutation

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceFoundException
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceNotFoundException
import pl.kamilszymanski707.eshopapi.services.discount.client.CatalogClient
import pl.kamilszymanski707.eshopapi.services.discount.data.domain.Coupon
import pl.kamilszymanski707.eshopapi.services.discount.data.repository.CouponRepository
import pl.kamilszymanski707.eshopapi.services.discount.event.CouponAmountUpdated
import pl.kamilszymanski707.eshopapi.services.discount.event.CouponAmountUpdatedEvent
import pl.kamilszymanski707.eshopapi.services.discount.event.CouponRemovedEvent
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
            throw ResourceFoundException("Coupon for product with id: ${input.productId} already exists.")

        return saveOrUpdate(null, input.productId, input.description, input.amount)
    }

    fun updateCoupon(input: CouponUpdateInput): CouponOutput {
        val coupon = couponRepository.findByProductId(input.productId)
            .orElseThrow { ResourceNotFoundException("Coupon for product with id: ${input.productId} does not exists.") }

        return saveOrUpdate(coupon.id, input.productId, input.description, input.amount)
    }

    fun deleteCoupon(productId: String): Boolean {
        val coupon = couponRepository.findByProductId(productId)
            .orElseThrow { ResourceNotFoundException("Coupon for product with id: $productId does not exists.") }

        couponRepository.delete(coupon)

        applicationEventPublisher.publishEvent(
            CouponRemovedEvent(this, productId))

        return !couponRepository.existsById(coupon.id!!)
    }

    private fun saveOrUpdate(
        couponId: Int?,
        productId: String,
        description: String,
        amount: Int,
    ): CouponOutput {
        val productOutput = catalogClient.getProductsByQuery(productId, null, null)
            ?: throw ResourceNotFoundException("Product with id: $productId does not exists.")

        val products = productOutput.data?.getProductsByQuery
        if (products == null || products.isEmpty() || products.size > 1)
            throw ResourceNotFoundException("Product with id: $productId does not exists.")

        var coupon = Coupon.createInstance(couponId, description, products[0].id, amount)
        coupon = couponRepository.save(coupon)

        if (couponId == null)
            applicationEventPublisher.publishEvent(CouponAmountUpdatedEvent(
                this, CouponAmountUpdated(coupon.productId!!, coupon.amount!!)))

        return CouponOutput(coupon.id!!, coupon.description!!, coupon.productId!!, coupon.amount!!)
    }
}
