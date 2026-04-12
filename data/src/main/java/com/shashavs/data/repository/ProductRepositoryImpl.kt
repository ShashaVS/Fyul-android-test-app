package com.shashavs.data.repository

import com.shashavs.data.network.ProductsService
import com.shashavs.domain.model.ProductsPage
import com.shashavs.domain.repository.ProductRepository
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productsService: ProductsService
) : ProductRepository {

    override suspend fun getProducts(
        skip: Int,
        limit: Int
    ): Result<ProductsPage> {
        // TODO implement paging logic
        return try {
            val response = productsService.getProducts()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    Result.success(body.toDomain())
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Result.failure(Exception("Server error: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Check your internet connection", e))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}