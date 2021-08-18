package pl.kamilszymanski707.eshopapi.services.catalog.data.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import pl.kamilszymanski707.eshopapi.services.catalog.data.QueryableProduct
import java.math.BigDecimal

@Document("products")
class Product : QueryableProduct {

    @field:Id
    @field:Indexed(unique = true)
    override var id: String? = null

    @field:Field(name = "name")
    @field:Indexed(unique = true)
    override var name: String? = null

    @field:Indexed
    @field:Field(name = "category")
    override var category: ProductCategory? = null

    @field:Field(name = "price")
    var price: BigDecimal? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false
        if (name != other.name) return false
        if (category != other.category) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (category?.hashCode() ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Product(id=$id, name=$name, category=$category, price=$price)"
    }

    companion object {

        fun createInstance(
            id: String?, name: String?,
            category: ProductCategory?,
            price: BigDecimal?,
        ): Product {

            val result = Product()
            result.id = id
            result.name = name
            result.category = category
            result.price = price

            return result
        }
    }
}

enum class ProductCategory {
    ELECTRONICS,
    AUTOMOTIVE
}
