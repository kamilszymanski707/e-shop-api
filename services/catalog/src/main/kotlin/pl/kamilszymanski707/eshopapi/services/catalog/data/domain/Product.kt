package pl.kamilszymanski707.eshopapi.services.catalog.data.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.math.BigDecimal

@Document
data class Product(
    @Id
    val id: String?,

    @Field(name = "name")
    val name: String,

    @Field(name = "category")
    val category: ProductCategory,

    @Field(name = "price")
    val price: BigDecimal,
)

enum class ProductCategory {
    ELECTRONICS,
    AUTOMOTIVE
}
