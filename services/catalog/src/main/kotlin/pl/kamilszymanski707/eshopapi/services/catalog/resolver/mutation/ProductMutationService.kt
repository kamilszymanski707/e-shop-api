package pl.kamilszymanski707.eshopapi.services.catalog.resolver.mutation

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceFoundException
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceNotFoundException
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.Product
import pl.kamilszymanski707.eshopapi.services.catalog.data.repository.ProductRepository
import pl.kamilszymanski707.eshopapi.services.catalog.event.ProductPriceUpdatedEvent
import pl.kamilszymanski707.eshopapi.services.catalog.resolver.ProductOutput

@Service
internal class ProductMutationService(
    private val productRepository: ProductRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun createProduct(input: ProductCreateInput): ProductOutput {
        val optionalProduct = productRepository.findByName(input.name)
        if (optionalProduct.isPresent)
            throw ResourceFoundException("Product with name: ${input.name} already exists.")

        var product = Product.createInstance(null, input.name, input.category, input.price)
        product = productRepository.save(product)
        return ProductOutput(product.id!!, product.name!!, product.category!!, input.price)
    }

    fun updateProduct(input: ProductUpdateInput): ProductOutput {
        var optionalProduct = productRepository.findById(input.id)
        if (optionalProduct.isEmpty)
            throw ResourceNotFoundException("Product with id: ${input.id} not found.")

        optionalProduct = productRepository.findByName(input.name)
        if (optionalProduct.isPresent && optionalProduct.get().name != input.name)
            throw ResourceFoundException("Product with name: ${input.name} already exists.")

        var product = Product.createInstance(input.id, input.name, input.category, input.price)
        product = productRepository.save(product)

        applicationEventPublisher.publishEvent(ProductPriceUpdatedEvent(this, product))
        return ProductOutput(product.id!!, product.name!!, product.category!!, input.price)
    }

    fun deleteProduct(id: String): Boolean {
        val product = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Product with id: $id not found.") }

        productRepository.delete(product)
        return !productRepository.existsById(id)
    }
}