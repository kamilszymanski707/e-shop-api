package pl.kamilszymanski707.eshopapi.services.catalog.resolver

import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.ProductCategory
import java.math.BigDecimal

data class ProductOutput(
    val id: String,
    val name: String,
    val category: ProductCategory,
    val price: BigDecimal,
)
