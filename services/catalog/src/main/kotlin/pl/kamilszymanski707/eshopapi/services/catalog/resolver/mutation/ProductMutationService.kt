package pl.kamilszymanski707.eshopapi.services.catalog.resolver.mutation

import graphql.GraphQLException
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.Product
import pl.kamilszymanski707.eshopapi.services.catalog.data.repository.ProductRepository
import pl.kamilszymanski707.eshopapi.services.catalog.resolver.ProductOutput

@Service
internal class ProductMutationService(
    private val productRepository: ProductRepository,
) {

    fun createProduct(input: ProductCreateInput): ProductOutput {
        val optionalProduct = productRepository.findByName(input.name)
        if (optionalProduct.isPresent) {
            throw GraphQLException("Product with name: ${input.name} already exist.")
        }

        var product = Product(null, input.name, input.category)
        product = productRepository.save(product)
        return ProductOutput(product.id!!, product.name, product.category)
    }

    fun updateProduct(input: ProductUpdateInput): ProductOutput {
        var optionalProduct = productRepository.findById(input.id)
        if (optionalProduct.isEmpty) {
            throw GraphQLException("Product with id: ${input.id} not found.")
        }

        optionalProduct = productRepository.findByName(input.name)
        if (optionalProduct.isPresent && optionalProduct.get().name != input.name) {
            throw GraphQLException("Product with name: ${input.name} already exist.")
        }

        var product = Product(input.id, input.name, input.category)
        product = productRepository.save(product)

        return ProductOutput(product.id!!, product.name, product.category)
    }

    fun deleteProduct(id: String): Boolean {
        val optionalProduct = productRepository.findById(id)
        if (optionalProduct.isEmpty) {
            throw GraphQLException("Product with id: $id not found.")
        }

        val product = optionalProduct.get()
        productRepository.delete(product)

        return productRepository.existsById(id)
    }
}