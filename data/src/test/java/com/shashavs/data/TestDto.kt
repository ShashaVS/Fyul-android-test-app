package com.shashavs.data

import com.shashavs.data.dto.DimensionsDto
import com.shashavs.data.dto.MetaDto
import com.shashavs.data.dto.ProductDto
import com.shashavs.data.dto.ProductsResponseDto
import com.shashavs.data.dto.ReviewDto
import com.shashavs.domain.model.Dimensions
import com.shashavs.domain.model.Meta
import com.shashavs.domain.model.Product
import com.shashavs.domain.model.ProductsPage
import com.shashavs.domain.model.Review

object TestDto {
    val dimensionsDto = DimensionsDto(width = 10.0, height = 20.0, depth = 30.0)
    val dimensions = Dimensions(width = 10.0, height = 20.0, depth = 30.0)

    val reviewDto = ReviewDto(
        rating = 5,
        comment = "Great",
        date = "2023-01-01",
        reviewerName = "John",
        reviewerEmail = "john@example.com"
    )
    val review = Review(
        rating = 5,
        comment = "Great",
        date = "2023-01-01",
        reviewerName = "John",
        reviewerEmail = "john@example.com"
    )

    val metaDto = MetaDto(
        createdAt = "2023-01-01",
        updatedAt = "2023-01-02",
        barcode = "123",
        qrCode = "qr"
    )
    val meta = Meta(
        createdAt = "2023-01-01",
        updatedAt = "2023-01-02",
        barcode = "123",
        qrCode = "qr"
    )

    val productDto = ProductDto(
        id = 1,
        title = "Product",
        description = "Desc",
        category = "Cat",
        price = 10.0,
        discountPercentage = 1.0,
        rating = 4.0,
        stock = 10,
        tags = emptyList(),
        brand = "Brand",
        sku = "SKU",
        weight = 1,
        dimensions = dimensionsDto,
        warrantyInformation = "1y",
        shippingInformation = "Ship",
        availabilityStatus = "In Stock",
        reviews = listOf(reviewDto),
        returnPolicy = "30d",
        minimumOrderQuantity = 1,
        meta = metaDto,
        images = emptyList(),
        thumbnail = "thumb"
    )

    val product = Product(
        id = 1,
        title = "Product",
        description = "Desc",
        category = "Cat",
        price = 10.0,
        discountPercentage = 1.0,
        rating = 4.0,
        stock = 10,
        tags = emptyList(),
        brand = "Brand",
        sku = "SKU",
        weight = 1,
        dimensions = dimensions,
        warrantyInformation = "1y",
        shippingInformation = "Ship",
        availabilityStatus = "In Stock",
        reviews = listOf(review),
        returnPolicy = "30d",
        minimumOrderQuantity = 1,
        meta = meta,
        images = emptyList(),
        thumbnail = "thumb"
    )

    val productsResponseDto = ProductsResponseDto(
        products = listOf(productDto),
        total = 100,
        skip = 0,
        limit = 30
    )

    val productsPage = ProductsPage(
        items = listOf(product),
        total = 100,
        skip = 0,
        limit = 30
    )
}