package pl.kamilszymanski707.eshopapi.services.catalog.resolver.mutation

import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import pl.kamilszymanski707.eshopapi.services.catalog.resolver.ProductOutput
import javax.validation.Valid

@Component
@Validated
@PreAuthorize("hasRole('admin')")
internal class CatalogMutationResolver(
    private val productMutationService: ProductMutationService,
) : GraphQLMutationResolver {

    fun createProduct(@Valid input: ProductCreateInput): ProductOutput =
        productMutationService.createProduct(input)

    fun updateProduct(@Valid input: ProductUpdateInput): ProductOutput =
        productMutationService.updateProduct(input)

    fun deleteProduct(id: String): Boolean =
        productMutationService.deleteProduct(id)
}