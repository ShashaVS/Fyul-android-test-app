package com.shashavs.domain.usecases

import com.shashavs.domain.model.ProductsPage
import com.shashavs.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(
        skip: Int,
        limit: Int
    ): Result<ProductsPage> {
        return productRepository.getProducts(skip, limit)
    }
}