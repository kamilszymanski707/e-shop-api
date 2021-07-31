package pl.kamilszymanski707.eshopapi.services.catalog.resolver.query

import pl.kamilszymanski707.eshopapi.services.catalog.data.QueryableProduct
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.ProductCategory

internal data class ProductQueryInput(

    override var id: String?,
    override var name: String?,
    override var category: ProductCategory?,
) : QueryableProduct
