package com.shashavs.data.repository

import com.shashavs.data.dto.ProductsResponseDto
import com.shashavs.data.network.ProductsService
import com.shashavs.domain.model.AppError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ProductRepositoryImplTest {
    private val service: ProductsService = mockk()
    private val repository = ProductRepositoryImpl(service)
    private val skip = 0
    private val limit = 30

    @Test
    fun `getProducts returns success`() = runTest {
        val dto = ProductsResponseDto(products = emptyList(), total = 0, skip = 0, limit = 30)

        coEvery { service.getProducts(any(), any()) } returns Response.success(dto)
        val actual = repository.getProducts(skip, limit)

        assertTrue(actual.isSuccess)
        assertEquals(dto.toDomain(), actual.getOrNull())
        coVerify { service.getProducts(skip, limit) }
    }

    @Test
    fun `getProducts returns failure with IOException`() = runTest {
        coEvery { service.getProducts(any(), any()) } throws IOException()
        val actual = repository.getProducts(skip, limit)

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is AppError.NetworkError)
        coVerify { service.getProducts(skip, limit) }
    }

    @Test
    fun `getProducts returns failure with UnknownError`() = runTest {
        coEvery { service.getProducts(any(), any()) } throws Exception()
        val actual = repository.getProducts(skip, limit)

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is AppError.UnknownError)
        coVerify { service.getProducts(skip, limit) }
    }

    @Test
    fun `getProducts returns failure with Server error`() = runTest {
        coEvery { service.getProducts(any(), any()) } returns Response.error(500, "".toResponseBody())
        val actual = repository.getProducts(skip, limit)

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is AppError.ServerError)
        coVerify { service.getProducts(skip, limit) }
    }

    @Test
    fun `getProducts returns failure with empty response body`() = runTest {
        coEvery { service.getProducts(any(), any()) } returns Response.success(null)
        val actual = repository.getProducts(skip, limit)

        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is AppError.UnknownError)
        coVerify { service.getProducts(skip, limit) }
    }
}