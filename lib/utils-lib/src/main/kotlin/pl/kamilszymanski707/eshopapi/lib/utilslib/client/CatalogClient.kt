package pl.kamilszymanski707.eshopapi.lib.utilslib.client

import org.springframework.web.client.RestTemplate
import pl.kamilszymanski707.eshopapi.lib.utilslib.constant.ClientConstant.Companion.CATALOG_SERVICE_URI
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceNotFoundException
import java.math.BigDecimal

interface CatalogClient {

    fun getProductById(productId: String): ProductItemOutput

    fun getProductsByQuery(
        id: String?,
        name: String?,
        category: String?,
    ): ProductOutput?

    companion object {

        fun catalogClient(restTemplate: RestTemplate): CatalogClient =
            CatalogClientImpl(restTemplate)
    }
}

internal class CatalogClientImpl(
    restTemplate: RestTemplate,
) : GraphQLClient<ProductOutput>(ProductOutput::class.java, restTemplate),
    CatalogClient {

    override fun getProductById(productId: String): ProductItemOutput {
        val productOutput = getProductsByQuery(productId, null, null)
            ?: throw ResourceNotFoundException("Product with id: $productId does not exists.")

        val products = productOutput.data?.getProductsByQuery
        if (products == null || products.isEmpty() || products.size > 1)
            throw ResourceNotFoundException("Product with id: $productId does not exists.")

        return products[0]
    }

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
