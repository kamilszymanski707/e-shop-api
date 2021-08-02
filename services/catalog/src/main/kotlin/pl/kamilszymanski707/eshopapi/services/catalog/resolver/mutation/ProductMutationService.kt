package pl.kamilszymanski707.eshopapi.services.catalog.resolver.mutation

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceFoundException
import pl.kamilszymanski707.eshopapi.lib.utilslib.exception.ResourceNotFoundException
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.Product
import pl.kamilszymanski707.eshopapi.services.catalog.data.repository.ProductRepository
import pl.kamilszymanski707.eshopapi.services.catalog.event.ProductPriceUpdated
import pl.kamilszymanski707.eshopapi.services.catalog.event.ProductPriceUpdatedEvent
import pl.kamilszymanski707.eshopapi.services.catalog.event.ProductRemovedEvent
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

        val existingProduct = optionalProduct.get()

        var product = Product.createInstance(input.id, input.name, input.category, input.price)
        product = productRepository.save(product)

        if (existingProduct.price != input.price)
            applicationEventPublisher.publishEvent(ProductPriceUpdatedEvent(
                this, ProductPriceUpdated(product.id!!, product.price!!)))

        return ProductOutput(product.id!!, product.name!!, product.category!!, input.price)
    }

    fun deleteProduct(id: String): Boolean {
        val product = productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Product with id: $id not found.") }

        productRepository.delete(product)

        val isRemoved = !productRepository.existsById(id)

        if (isRemoved)
            applicationEventPublisher.publishEvent(ProductRemovedEvent(this, id))

        return isRemoved
    }
}