package pl.kamilszymanski707.eshopapi.services.discount.client

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.kamilszymanski707.eshopapi.services.discount.exception.ResourceNotFoundException
import java.math.BigDecimal
import java.net.URI

interface CatalogClient {

    fun getProductsByQuery(
        id: String?,
        name: String?,
        category: String?,
    ): ProductOutput?
}

@Component
internal class CatalogClientImpl(
    private val restTemplate: RestTemplate,
) : CatalogClient {

    private val logger: Logger = LoggerFactory.getLogger(CatalogClientImpl::class.java)

    private val url = "lb://catalog/graphql"

    override fun getProductsByQuery(
        id: String?,
        name: String?,
        category: String?,
    ): ProductOutput? {
        val headers = HttpHeaders()
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE)

        val idParam = if (id != null) """\"$id\"""" else null
        val nameParam = if (name != null) """\"$name\"""" else null
        val categoryParam = if (category != null) """\"$category\"""" else null

        val gql = """
            {
                "query": "query {  
                    getProductsByQuery(input: {
                        id: $idParam, 
                        name: $nameParam, 
                        category: $categoryParam            
                    }) {
                        id    
                        name    
                        category    
                        price  
                    }
                }"
            }
        """.trimIndent()

        try {
            return restTemplate.postForObject(
                URI(url),
                HttpEntity(gql, headers),
                ProductOutput::class.java)
        } catch (e: Exception) {
            logger.error(e.message)
            throw ResourceNotFoundException("Internal Server Error.")
        }
    }
}

data class ProductOutput(
    val data: ProductDataOutput?,
)

data class ProductDataOutput(
    val getProductsByQuery: List<ProductItemOutput>,
)

data class ProductItemOutput(
    val id: String?,
    val name: String?,
    val category: String?,
    val price: BigDecimal?,
)
