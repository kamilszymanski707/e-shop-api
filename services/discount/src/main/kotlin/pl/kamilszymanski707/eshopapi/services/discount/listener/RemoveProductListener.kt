package pl.kamilszymanski707.eshopapi.services.discount.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.LoggerConstant.Companion.LOGGER
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.RabbitMQConstant.Companion.PRODUCT_REMOVED_QUEUE
import pl.kamilszymanski707.eshopapi.services.discount.data.dao.CouponDao
import pl.kamilszymanski707.eshopapi.services.discount.data.domain.Coupon
import pl.kamilszymanski707.eshopapi.services.discount.data.repository.CouponRepository

@Component
@RabbitListener(queues = [PRODUCT_REMOVED_QUEUE])
internal class RemoveProductListener(
    private val couponDao: CouponDao,
    private val couponRepository: CouponRepository,
) {
    private val mapper = jacksonObjectMapper()

    @RabbitHandler(isDefault = true)
    fun handle(bytea: ByteArray) {
        val value = mapper.readValue(bytea, String::class.java)

        LOGGER.info("Message received {}", value)

        val all = couponDao.findByQuery(
            Coupon.createInstance(null, null, value, null))

        all.forEach { handleRemove(it, value) }
    }

    private fun handleRemove(
        coupon: Coupon,
        productId: String,
    ) {
        LOGGER.info("Coupon {}", coupon.toString())
        LOGGER.info("Product ID {}", productId)

        couponRepository.delete(coupon)

        LOGGER.info("Coupon removed")
    }
}
