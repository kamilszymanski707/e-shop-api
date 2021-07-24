package pl.kamilszymanski707.eshopapi.services.catalog.resolver.query

import pl.kamilszymanski707.eshopapi.services.catalog.data.QueryableProduct
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.ProductCategory

internal data class ProductQueryInput(
    val id: String?,
    val name: String?,
    val category: ProductCategory?,
) : QueryableProduct
