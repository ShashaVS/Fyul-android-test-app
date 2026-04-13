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
                    Result.failure(AppError.UnknownError(msg = "Response body is null"))
                }
            } else {
                Result.failure(AppError.ServerError())
            }
        } catch (e: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (e: Exception) {
            Result.failure(AppError.UnknownError(msg = e.message))
        }
    }
}