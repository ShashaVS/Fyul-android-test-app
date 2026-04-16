package com.shashavs.fyultest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shashavs.fyultest.R
import com.shashavs.fyultest.ui.components.ProductListItems
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    productListViewModel: ProductListViewModel = hiltViewModel(),
) {
    val uiState by productListViewModel.uiState.collectAsState()

    ProductListContent(
        uiState = uiState,
        onRetry = { productListViewModel.loadProducts() },
        onLoadMore = { productListViewModel.loadProducts() },
        modifier = modifier
    )
}

@Composable
fun ProductListContent(
    uiState: ProductListUI,
    onRetry: () -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (val state = uiState) {
        is ProductListUI.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.products_loading),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                CircularProgressIndicator()
            }
        }
        is ProductListUI.Success -> {
            val listState = rememberLazyListState()

            LaunchedEffect(listState, state.canLoadMore, state.isLoadingMore) {
                snapshotFlow {
                    val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                    lastVisibleItem?.index ?: 0
                }
                    .distinctUntilChanged()
                    .filter { lastIndex ->
                        val threshold = state.products.size - 5
                        lastIndex >= threshold && state.canLoadMore && !state.isLoadingMore
                    }
                    .collect {
                        onLoadMore()
                    }
            }

            LazyColumn(
                state = listState,
                modifier = modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = state.products,
                    key = { _, product -> product.id }
                ) { _, product ->
                    ProductListItems(product)
                }

                if (state.isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
        is ProductListUI.Error -> {
            Column(
                modifier = modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(state.resId),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onRetry
                ) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
    }
}
