package pl.kamilszymanski707.eshopapi.services.catalog.event

import org.springframework.context.ApplicationEvent
import pl.kamilszymanski707.eshopapi.services.catalog.data.domain.Product

class ProductPriceUpdatedEvent(
    source: Any,
    val product: Product,
) : ApplicationEvent(source)
