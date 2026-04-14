package com.shashavs.fyultest.ui.screens

import com.shashavs.domain.model.AppError
import com.shashavs.domain.model.Dimensions
import com.shashavs.domain.model.Meta
import com.shashavs.domain.model.Product
import com.shashavs.domain.model.ProductsPage
import com.shashavs.domain.usecases.GetProductsUseCase
import com.shashavs.fyultest.MainDispatcherRule
import com.shashavs.fyultest.R
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getProductsUseCase: GetProductsUseCase = mockk()

    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setup() = runTest {
        viewModel = ProductListViewModel(getProductsUseCase)
    }

    @Test
    fun `initial state is Loading`() {
        assertEquals(ProductListUI.Loading, viewModel.uiState.value)
    }

    @Test
    fun `loadProducts success updates uiState to Success`() = runTest {
        val productsPage = ProductsPage(
            items = listOf(createProduct(1), createProduct(2)),
            total = 2,
            skip = 0,
            limit = 30
        )

        coEvery { getProductsUseCase(0, 30) } returns Result.success(productsPage)

        val job = launch {
            viewModel.uiState.collect()
        }
        advanceUntilIdle()
        val state = viewModel.uiState.value

        assert(state is ProductListUI.Success)
        assertEquals(2, (state as ProductListUI.Success).products.size)
        assertEquals(1, state.products[0].id)
        assertEquals(2, state.products[1].id)

        job.cancel()
    }

    @Test
    fun `loadProducts NetworkError updates uiState to Error`() = runTest {
        coEvery { getProductsUseCase(0, 30) } returns Result.failure(AppError.NetworkError())

        val job = launch {
            viewModel.uiState.collect()
        }
        advanceUntilIdle()
        val state = viewModel.uiState.value

        assert(state is ProductListUI.Error)
        assertEquals(R.string.network_error, (state as ProductListUI.Error).resId)

        job.cancel()
    }

    @Test
    fun `loadProducts ServerError updates uiState to Error`() = runTest {
        coEvery { getProductsUseCase(0, 30) } returns Result.failure(AppError.ServerError())

        val job = launch {
            viewModel.uiState.collect()
        }
        advanceUntilIdle()
        val state = viewModel.uiState.value

        assert(state is ProductListUI.Error)
        assertEquals(R.string.server_error, (state as ProductListUI.Error).resId)

        job.cancel()
    }

    private fun createProduct(id: Int) = Product(
        id = id,
        title = "Product $id",
        description = "Description $id",
        category = "Category",
        price = 10.0,
        discountPercentage = 0.0,
        rating = 4.5,
        stock = 10,
        tags = emptyList(),
        brand = "Brand",
        sku = "SKU",
        weight = 1,
        dimensions = Dimensions(1.0, 1.0, 1.0),
        warrantyInformation = "",
        shippingInformation = "",
        availabilityStatus = "",
        reviews = emptyList(),
        returnPolicy = "",
        minimumOrderQuantity = 1,
        meta = Meta("", "", "", ""),
        images = emptyList(),
        thumbnail = "url"
    )
}
