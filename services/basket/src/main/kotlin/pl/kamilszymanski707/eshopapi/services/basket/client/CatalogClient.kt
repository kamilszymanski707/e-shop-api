package pl.kamilszymanski707.eshopapi.services.basket.client

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.kamilszymanski707.eshopapi.lib.utilslib.client.GraphQLClient
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.ClientConstant.Companion.CATALOG_SERVICE_URI
import java.math.BigDecimal

interface CatalogClient {

    fun getProductsByQuery(
        id: String?,
        name: String?,
        category: String?,
    ): ProductOutput?
}

@Component
internal class CatalogClientImpl(
    restTemplate: RestTemplate,
) : GraphQLClient<ProductOutput>(ProductOutput::class.java, restTemplate),
    CatalogClient {

    override fun getProductsByQuery(
        id: String?,
        name: String?,
        category: String?,
    ): ProductOutput? {

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

        return query(gql, CATALOG_SERVICE_URI)
    }
}

data class ProductOutput(
    val data: ProductDataOutput?,
)

data class ProductDataOutput(
    val getProductsByQuery: List<ProductItemOutput>,
)

data class ProductItemOutput(
    val id: String,
    val name: String,
    val category: String,
    val price: BigDecimal,
)
