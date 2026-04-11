package com.shashavs.data.repository

import com.shashavs.domain.model.ProductsPage
import com.shashavs.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(

) : ProductRepository {

    override suspend fun getProducts(
        skip: Int,
        limit: Int
    ): ProductsPage {
        TODO("Not yet implemented")
    }
}