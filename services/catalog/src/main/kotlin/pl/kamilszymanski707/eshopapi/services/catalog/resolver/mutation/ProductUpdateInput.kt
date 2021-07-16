package pl.kamilszymanski707.eshopapi.services.catalog.resolver.mutation

import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.ProductCategory
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

internal data class ProductUpdateInput(

    @field:NotEmpty(message = "Product id cannot be empty.")
    val id: String,

    @field:NotNull(message = "Product name cannot be empty.")
    @field:Size(min = 3, max = 20, message = "Product name must be between 3 and 20 characters long.")
    val name: String,

    @field:NotNull(message = "Product category cannot be empty.")
    val category: ProductCategory,
)