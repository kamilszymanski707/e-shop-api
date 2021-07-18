package pl.kamilszymanski707.eshopapi.services.discount.service

import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.services.discount.client.CatalogClient
import pl.kamilszymanski707.eshopapi.services.discount.data.domain.Coupon
import pl.kamilszymanski707.eshopapi.services.discount.data.repository.CouponRepository
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponCreateDto
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponDto
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponUpdateDto
import pl.kamilszymanski707.eshopapi.services.discount.exception.ResourceFoundException
import pl.kamilszymanski707.eshopapi.services.discount.exception.ResourceNotFoundException

interface CouponService {

    fun getCoupon(productId: String): CouponDto

    fun createCoupon(dto: CouponCreateDto): CouponDto

    fun updateCoupon(dto: CouponUpdateDto): CouponDto

    fun deleteCoupon(id: Int): Boolean
}

@Service
internal class CouponServiceImpl(
    private val couponRepository: CouponRepository,
    private val catalogClient: CatalogClient,
) : CouponService {

    override fun getCoupon(productId: String): CouponDto {
        val coupon = couponRepository.findByProductId(productId)
            .orElseThrow { ResourceNotFoundException("Coupon for product with id: $productId not found.") }

        return CouponDto(coupon.id!!, coupon.description, coupon.productId, coupon.amount)
    }

    override fun createCoupon(dto: CouponCreateDto): CouponDto {
        val optionalCoupon = couponRepository.findByProductId(dto.productId)
        if (optionalCoupon.isPresent)
            throw ResourceFoundException("Coupon for product with id: ${dto.productId} already exist.")

        return saveOrUpdate(null, dto.productId, dto.description, dto.amount)
    }

    override fun updateCoupon(dto: CouponUpdateDto): CouponDto {
        val coupon = couponRepository.findByProductId(dto.productId)
            .orElseThrow { ResourceNotFoundException("Coupon for product with id: ${dto.productId} does not exist.") }

        return saveOrUpdate(coupon.id, dto.productId, dto.description, dto.amount)
    }

    override fun deleteCoupon(id: Int): Boolean {
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
    ): CouponDto {
        val productOutput = catalogClient.getProductsByQuery(productId, null, null)
            ?: throw ResourceNotFoundException("Product with id: $productId does not exist.")

        val products = productOutput.data?.getProductsByQuery
        if (products == null || products.isEmpty() || products.size > 1)
            throw ResourceNotFoundException("Product with id: $productId does not exist.")

        var coupon = Coupon(couponId, description, products[0].id!!, amount)
        coupon = couponRepository.save(coupon)

        return CouponDto(coupon.id!!, coupon.description, coupon.productId, coupon.amount)
    }
}
