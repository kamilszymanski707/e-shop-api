package pl.kamilszymanski707.eshopapi.services.catalog.data.repository

import org.springframework.data.mongodb.repository.MongoRepository
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.Product
import java.util.*

interface ProductRepository : MongoRepository<Product, String> {

    fun findByName(name: String): Optional<Product>
}