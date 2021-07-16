package pl.kamilszymanski707.eshopapi.services.catalog.resolver

import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.ProductCategory

data class ProductOutput(
    val id: String,
    val name: String,
    val category: ProductCategory,
)
