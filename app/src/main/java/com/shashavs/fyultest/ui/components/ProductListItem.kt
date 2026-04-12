package com.shashavs.fyultest.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shashavs.fyultest.ui.models.ProductUI

@Composable
fun ProductListItems(
    product: ProductUI,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        leadingContent = {
            // TODO impl load product thumbnail
        },
        headlineContent = {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
    )
}

@Preview
@Composable
fun PreviewProductListItems() {
    ProductListItems(
        product = ProductUI(
            id = 1,
            title = "Product Title",
            description = "Product Description",
            thumbnail = ""
        )
    )
}