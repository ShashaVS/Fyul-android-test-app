package com.shashavs.fyultest.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shashavs.fyultest.ui.models.ProductUI

@Composable
fun ProductListItems(
    product: ProductUI,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },

        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp)),
                model = product.thumbnail,
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                contentDescription = product.title,
            )
        },
        headlineContent = {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
            description = "Product Description Product Description Product Description " +
                    "Product Description Product Description",
            thumbnail = ""
        )
    )
}