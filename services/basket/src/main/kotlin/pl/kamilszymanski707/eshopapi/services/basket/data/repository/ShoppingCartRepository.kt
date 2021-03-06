package pl.kamilszymanski707.eshopapi.services.basket.data.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.QueryByExampleExecutor
import pl.kamilszymanski707.eshopapi.services.basket.data.domain.ShoppingCart

interface ShoppingCartRepository :
    CrudRepository<ShoppingCart, String>,
    QueryByExampleExecutor<ShoppingCart>