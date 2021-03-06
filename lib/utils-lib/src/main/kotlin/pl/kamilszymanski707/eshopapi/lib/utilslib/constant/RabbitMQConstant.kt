package pl.kamilszymanski707.eshopapi.lib.utilslib.constant

class RabbitMQConstant {

    companion object {

        const val COUPON_AMOUNT_UPDATED_QUEUE = "coupon-amount-updated-queue"
        const val COUPON_REMOVED_QUEUE = "coupon-removed-queue"
        const val PRODUCT_PRICE_UPDATED_QUEUE = "product-price-updated-queue"
        const val PRODUCT_REMOVED_QUEUE = "product-removed-queue"
    }
}