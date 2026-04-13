package com.shashavs.domain.usecases

import com.shashavs.domain.model.AppError
import com.shashavs.domain.model.ProductsPage
import com.shashavs.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetProductsUseCaseTest {

    private val repository = mockk<ProductRepository>()
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setup() {
        getProductsUseCase = GetProductsUseCase(repository)
    }

    @Test
    fun `success result from repository`() = runTest {
        val skip = 0
        val limit = 30
        val page = ProductsPage(
            items = emptyList(),
            total = 1,
            skip = skip,
            limit = limit
        )

        coEvery { repository.getProducts(skip, limit) } returns Result.success(page)
        val actual = getProductsUseCase(skip, limit)

        assert(actual.isSuccess)
        assertEquals(Result.success(page), actual)
        coVerify { repository.getProducts(skip, limit) }
    }

    @Test
    fun `fail result from repository`() = runTest {
        val skip = 0
        val limit = 30
        val error = AppError.NetworkError()

        coEvery { repository.getProducts(skip, limit) } returns Result.failure(error)
        val actual = getProductsUseCase(skip, limit)

        assert(actual.isFailure)
        assertEquals(Result.failure<ProductsPage>(error), actual)
        coVerify { repository.getProducts(skip, limit) }
    }

}