package pl.kamilszymanski707.eshopapi.services.catalog.resolver.query

import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.services.catalog.data.dao.ProductDao
import pl.kamilszymanski707.eshopapi.services.catalog.resolver.ProductOutput

@Service
internal class ProductQueryService(
    private val productDao: ProductDao,
) {
    fun getProductsByQuery(input: ProductQueryInput?): List<ProductOutput> =
        productDao.findByQuery(input)
            .map { ProductOutput(it.id!!, it.name, it.category, it.price) }
}