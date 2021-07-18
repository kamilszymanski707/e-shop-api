package pl.kamilszymanski707.eshopapi.services.catalog.resolver.mutation

import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.ProductCategory
import java.math.BigDecimal
import javax.validation.constraints.*

internal data class ProductUpdateInput(

    @field:NotEmpty(
        message = "Product id cannot be empty.")
    val id: String,

    @field:NotNull(
        message = "Product name cannot be empty.")
    @field:Size(
        min = 3,
        max = 20,
        message = "Product name must be between 3 and 20 characters long.")
    val name: String,

    @field:NotNull(
        message = "Product category cannot be empty.")
    val category: ProductCategory,

    @field:Digits(
        integer = 12,
        fraction = 2,
        message = "The price can be 12 decimal and 2 places before the decimal point.")
    @field:DecimalMin(
        value = "0.0",
        inclusive = false)
    @field:NotNull(
        message = "Product price cannot be empty.")
    val price: BigDecimal,
)