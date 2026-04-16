package com.shashavs.fyultest.ui.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashavs.domain.model.AppError
import com.shashavs.domain.usecases.GetProductsUseCase
import com.shashavs.fyultest.R
import com.shashavs.fyultest.ui.models.ProductUI
import com.shashavs.fyultest.ui.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUI>(ProductListUI.Loading)
    val uiState: StateFlow<ProductListUI> = _uiState
        .onStart { loadProducts() }
        .stateIn(viewModelScope, SharingStarted.Lazily, ProductListUI.Loading)

    private var currentSkip = 0
    private val pageSize = 30
    private var isFetching = false

    fun loadProducts() {
        if (isFetching) return

        val currentState = _uiState.value
        if (currentState is ProductListUI.Success && !currentState.canLoadMore) return

        isFetching = true
        viewModelScope.launch {
            try {
                if (currentState is ProductListUI.Success) {
                    _uiState.update { currentState.copy(isLoadingMore = true) }
                } else {
                    _uiState.value = ProductListUI.Loading
                }

                getProductsUseCase(currentSkip, pageSize)
                    .onSuccess { page ->
                        val newProducts = page.items.map { it.toUI() }
                        _uiState.update { state ->
                            val products = if (state is ProductListUI.Success) {
                                state.products + newProducts
                            } else {
                                newProducts
                            }

                            ProductListUI.Success(
                                products = products,
                                isLoadingMore = false,
                                canLoadMore = products.size < page.total
                            )
                        }
                        currentSkip += page.items.size
                    }
                    .onFailure { error ->
                        _uiState.update { state ->
                            if (state is ProductListUI.Success) {
                                state.copy(isLoadingMore = false)
                            } else {
                                ProductListUI.Error(mapError(error))
                            }
                        }
                    }
            } finally {
                isFetching = false
            }
        }
    }

    @StringRes
    private fun mapError(error: Throwable): Int {
        return when (error) {
            is AppError.NetworkError -> R.string.network_error
            is AppError.ServerError -> R.string.server_error
            else -> R.string.unknown_error
        }
    }
}

sealed interface ProductListUI {
    data object Loading : ProductListUI
    data class Success(
        val products: List<ProductUI>,
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = true
    ) : ProductListUI
    data class Error(@StringRes val resId: Int) : ProductListUI
}