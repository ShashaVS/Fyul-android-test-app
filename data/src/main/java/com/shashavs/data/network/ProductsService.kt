package com.shashavs.data.network

import com.shashavs.data.dto.ProductsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Response<ProductsResponseDto>
}