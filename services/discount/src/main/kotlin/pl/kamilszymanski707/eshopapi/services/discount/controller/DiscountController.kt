package pl.kamilszymanski707.eshopapi.services.discount.controller

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponCreateDto
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponDto
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponUpdateDto
import pl.kamilszymanski707.eshopapi.services.discount.service.CouponService

@RestController
internal class DiscountController(
    private val couponService: CouponService,
) {

    @GetMapping("/{productId}")
    @ResponseStatus(OK)
    fun getDiscount(@PathVariable productId: String): CouponDto =
        couponService.getCoupon(productId)

    @PostMapping
    @ResponseStatus(CREATED)
    fun createDiscount(@RequestBody dto: CouponCreateDto): CouponDto =
        couponService.createCoupon(dto)

    @PutMapping
    @ResponseStatus(OK)
    fun updateDiscount(@RequestBody dto: CouponUpdateDto): CouponDto =
        couponService.updateCoupon(dto)

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    fun deleteDiscount(@PathVariable id: Int): Boolean =
        couponService.deleteCoupon(id)
}