package com.shashavs.fyultest.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.shashavs.fyultest.R
import com.shashavs.fyultest.ui.models.ProductUI
import org.junit.Rule
import org.junit.Test

class ProductListContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun loadingState_displaysLoadingText() {
        composeTestRule.setContent {
            ProductListContent(
                uiState = ProductListUI.Loading,
                onRetry = {}
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.products_loading))
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysProductList() {
        val products = listOf(
            ProductUI(1, "Product 1", "Description 1", "url1"),
            ProductUI(2, "Product 2", "Description 2", "url2")
        )

        composeTestRule.setContent {
            ProductListContent(
                uiState = ProductListUI.Success(products),
                onRetry = {}
            )
        }

        composeTestRule.onNode(hasText("Product 1") and hasText("Description 1"))
            .assertIsDisplayed()
        composeTestRule.onNode(hasText("Product 2") and hasText("Description 2"))
            .assertIsDisplayed()
    }

    @Test
    fun errorState_displaysErrorMessageAndRetryButton() {
        val errorMessageRes = R.string.network_error
        var retryClicked = false

        composeTestRule.setContent {
            ProductListContent(
                uiState = ProductListUI.Error(errorMessageRes),
                onRetry = { retryClicked = true }
            )
        }

        composeTestRule.onNodeWithText(context.getString(errorMessageRes)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.retry)).assertIsDisplayed()

        composeTestRule.onNodeWithText(context.getString(R.string.retry)).performClick()
        assert(retryClicked)
    }
}
