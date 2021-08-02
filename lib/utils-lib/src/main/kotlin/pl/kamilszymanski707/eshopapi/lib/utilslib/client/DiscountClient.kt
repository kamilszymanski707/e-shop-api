package pl.kamilszymanski707.eshopapi.lib.utilslib.client

import org.springframework.web.client.RestTemplate
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.ClientConstant.Companion.DISCOUNT_SERVICE_URI

interface DiscountClient {

    fun getCouponsByQuery(
        id: Int?,
        description: String?,
        productId: String?,
    ): CouponOutput?

    companion object {

        fun discountClient(restTemplate: RestTemplate): DiscountClient =
            DiscountClientImpl(restTemplate)
    }
}

internal class DiscountClientImpl(
    restTemplate: RestTemplate,
) : GraphQLClient<CouponOutput>(CouponOutput::class.java, restTemplate),
    DiscountClient {

    override fun getCouponsByQuery(
        id: Int?,
        description: String?,
        productId: String?,
    ): CouponOutput? {

        val idParam = if (id != null) """\"$id\"""" else null
        val descriptionParam = if (description != null) """\"$description\"""" else null
        val productIdParam = if (productId != null) """\"$productId\"""" else null

        val gql = """
            {
                "query": "query {  
                    getCouponsByQuery(input: {
                        id: $idParam, 
                        description: $descriptionParam, 
                        productId: $productIdParam            
                    }) {
                        id
                        description
                        productId
                        amount 
                    }
                }"
            }
        """.trimIndent()

        return query(gql, DISCOUNT_SERVICE_URI)
    }
}

data class CouponOutput(
    val data: CouponDataOutput?,
)

data class CouponDataOutput(
    val getCouponsByQuery: List<CouponItemData>,
)

data class CouponItemData(
    val id: Int,
    val description: String,
    val productId: String,
    val amount: Int,
)
