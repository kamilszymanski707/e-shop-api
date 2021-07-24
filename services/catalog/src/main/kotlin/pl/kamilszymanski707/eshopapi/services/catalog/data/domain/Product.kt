package pl.kamilszymanski707.eshopapi.services.catalog.data.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import pl.kamilszymanski707.eshopapi.services.catalog.data.QueryableProduct
import java.math.BigDecimal

@Document("products")
data class Product(

    @Id
    @Indexed(unique = true)
    val id: String?,

    @Field(name = "name")
    @Indexed(unique = true)
    val name: String,

    @Indexed
    @Field(name = "category")
    val category: ProductCategory,

    @Field(name = "price")
    val price: BigDecimal,
) : QueryableProduct

enum class ProductCategory {
    ELECTRONICS,
    AUTOMOTIVE
}
