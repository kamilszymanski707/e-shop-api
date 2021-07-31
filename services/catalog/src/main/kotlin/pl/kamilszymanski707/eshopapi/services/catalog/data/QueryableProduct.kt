package pl.kamilszymanski707.eshopapi.services.catalog.data

import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.ProductCategory

interface QueryableProduct {

    var id: String?
    var name: String?
    var category: ProductCategory?
}