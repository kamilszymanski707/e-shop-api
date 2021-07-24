package pl.kamilszymanski707.eshopapi.services.basket.client

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.kamilszymanski707.eshopapi.services.basket.exception.ResourceNotFoundException
import java.net.URI

interface DiscountClient {

    fun getCouponsByQuery(
        id: Int?,
        description: String?,
        productId: String?,
    ): CouponOutput?
}

@Component
internal class DiscountClientImpl(
    private val restTemplate: RestTemplate,
) : DiscountClient {

    private val logger: Logger = LoggerFactory.getLogger(DiscountClient::class.java)

    private val url = "lb://discount/graphql"

    override fun getCouponsByQuery(
        id: Int?,
        description: String?,
        productId: String?,
    ): CouponOutput? {
        val headers = HttpHeaders()
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE)

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

        try {
            return restTemplate.postForObject(
                URI(url),
                HttpEntity(gql, headers),
                CouponOutput::class.java)
        } catch (e: Exception) {
            logger.error(e.message)
            throw ResourceNotFoundException("Internal Server Error.")
        }
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
