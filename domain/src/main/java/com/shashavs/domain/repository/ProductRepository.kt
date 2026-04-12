package com.shashavs.domain.repository

import com.shashavs.domain.model.ProductsPage

interface ProductRepository {
    suspend fun getProducts(skip: Int, limit: Int): Result<ProductsPage>
}