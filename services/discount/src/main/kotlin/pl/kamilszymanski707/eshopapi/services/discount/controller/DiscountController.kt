package pl.kamilszymanski707.eshopapi.services.discount.controller

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponCreateDto
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponDto
import pl.kamilszymanski707.eshopapi.services.discount.dto.CouponUpdateDto
import pl.kamilszymanski707.eshopapi.services.discount.service.CouponService
import javax.validation.Valid

@RestController
@Validated
internal class DiscountController(
    private val couponService: CouponService,
) {

    @GetMapping("/{productId}")
    @ResponseStatus(OK)
    fun getDiscount(@PathVariable productId: String): CouponDto =
        couponService.getCoupon(productId)

    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasRole('admin')")
    fun createDiscount(@Valid @RequestBody dto: CouponCreateDto): CouponDto =
        couponService.createCoupon(dto)

    @PutMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('admin')")
    fun updateDiscount(@Valid @RequestBody dto: CouponUpdateDto): CouponDto =
        couponService.updateCoupon(dto)

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('admin')")
    fun deleteDiscount(@PathVariable id: Int): Boolean =
        couponService.deleteCoupon(id)
}