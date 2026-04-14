package com.shashavs.fyultest.ui.screens

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

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductListUI.Loading
            getProductsUseCase(0, 30)
                .onSuccess {
                    val products = it.items.map { it.toUI() }
                    _uiState.value = ProductListUI.Success(products)
                }
                .onFailure {
                    when(it) {
                        is AppError.NetworkError -> {
                            _uiState.value = ProductListUI.Error(R.string.network_error)
                        }
                        is AppError.ServerError -> {
                            _uiState.value = ProductListUI.Error(R.string.server_error)
                        }
                        is AppError.UnknownError -> {
                            _uiState.value = ProductListUI.Error(R.string.unknown_error)
                        }
                    }
                }
        }
    }
}

sealed interface ProductListUI {
    object Loading : ProductListUI
    data class Success(val products: List<ProductUI>) : ProductListUI
    data class Error(@StringRes val resId: Int) : ProductListUI
}