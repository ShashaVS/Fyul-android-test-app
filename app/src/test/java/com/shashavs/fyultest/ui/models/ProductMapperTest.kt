package com.shashavs.fyultest.ui.models

import com.shashavs.domain.model.Dimensions
import com.shashavs.domain.model.Meta
import com.shashavs.domain.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductMapperTest {

    @Test
    fun `Product toUI`() {
        val domainProduct = Product(
            id = 1,
            title = "Test Title",
            description = "Test Description",
            category = "Category",
            price = 99.99,
            discountPercentage = 10.0,
            rating = 4.5,
            stock = 50,
            tags = listOf("tag1", "tag2"),
            brand = "Brand",
            sku = "SKU",
            weight = 500,
            dimensions = Dimensions(10.0, 20.0, 30.0),
            warrantyInformation = "1 year",
            shippingInformation = "Fast shipping",
            availabilityStatus = "In stock",
            reviews = emptyList(),
            returnPolicy = "30 days",
            minimumOrderQuantity = 1,
            meta = Meta("created", "updated", "barcode", "qr"),
            images = listOf("img1", "img2"),
            thumbnail = "https://test.com/thumbnail.png"
        )
        val expected = ProductUI(
            id = 1,
            title = "Test Title",
            description = "Test Description",
            thumbnail = "https://test.com/thumbnail.png"
        )

        val actual = domainProduct.toUI()

        assertEquals(expected, actual)
    }
}
