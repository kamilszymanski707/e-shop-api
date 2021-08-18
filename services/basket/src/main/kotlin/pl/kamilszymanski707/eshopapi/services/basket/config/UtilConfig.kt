package pl.kamilszymanski707.eshopapi.services.basket.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kamilszymanski707.eshopapi.lib.utilslib.client.DiscountClient
import java.math.BigDecimal
import java.util.function.BiFunction

@Configuration
internal class UtilConfig {

    @Bean
    fun computePriceUtilBean(
        discountClient: DiscountClient,
    ): BiFunction<String, BigDecimal, BigDecimal> {
        return BiFunction { productId, price ->
            val discountOutput = discountClient.getCouponsByQuery(
                null, null, productId)

            if (discountOutput != null) {
                val coupons = discountOutput.data?.getCouponsByQuery
                if (coupons?.size == 0)
                    return@BiFunction price

                val coupon = coupons!![0]
                val multiplied = price.multiply(BigDecimal(coupon.amount.toDouble() / 100))

                return@BiFunction price.minus(multiplied)
            }

            return@BiFunction price
        }
    }
}