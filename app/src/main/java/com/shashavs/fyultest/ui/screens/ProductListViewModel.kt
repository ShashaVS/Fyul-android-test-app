package com.shashavs.fyultest.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashavs.domain.usecases.GetProductsUseCase
import com.shashavs.fyultest.ui.models.ProductUI
import com.shashavs.fyultest.ui.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUI>(ProductListUI.Loading)
    val uiState: StateFlow<ProductListUI> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductListUI.Loading
            getProductsUseCase(0, 30)
                .onSuccess {
                    val products = it.items.map { it.toUI() }
                    _uiState.value = ProductListUI.Success(products)
                }
                .onFailure {
                    _uiState.value = ProductListUI.Error(it.message ?: "Unknown error")
                }
        }
    }
}

sealed interface ProductListUI {
    object Loading : ProductListUI
    data class Success(val products: List<ProductUI>) : ProductListUI
    data class Error(val message: String) : ProductListUI
}