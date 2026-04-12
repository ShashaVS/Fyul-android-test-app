package com.shashavs.data.network

import com.shashavs.data.dto.ProductsResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface ProductsService {
    @GET("products")
    suspend fun getProducts(): Response<ProductsResponseDto>
}