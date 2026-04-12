package com.shashavs.fyultest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shashavs.fyultest.ui.components.ProductListItems

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    productListViewModel: ProductListViewModel = hiltViewModel(),
) {

    val uiState by productListViewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ProductListUI.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Products Loading...",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
                CircularProgressIndicator()
            }
        }
        is ProductListUI.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                items(state.products.size) { index ->
                    ProductListItems(state.products[index])
                }
            }
        }
        is ProductListUI.Error -> {
            // TODO impl Show error
        }
    }
}