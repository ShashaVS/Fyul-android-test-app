package com.shashavs.data.repository

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

fun ProductsResponseDto.toDomain(): ProductsPage {
    return ProductsPage(
        items = products.map { it.toDomain() },
        total = total,
        skip = skip,
        limit = limit
    )
}

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        tags = tags,
        brand = brand,
        sku = sku,
        weight = weight,
        dimensions = dimensions.toDomain(),
        warrantyInformation = warrantyInformation,
        shippingInformation = shippingInformation,
        availabilityStatus = availabilityStatus,
        reviews = reviews.map { it.toDomain() },
        returnPolicy = returnPolicy,
        minimumOrderQuantity = minimumOrderQuantity,
        meta = meta.toDomain(),
        images = images,
        thumbnail = thumbnail
    )
}

fun DimensionsDto.toDomain() = Dimensions(width, height, depth)

fun ReviewDto.toDomain() = Review(
    rating = rating,
    comment = comment,
    date = date,
    reviewerName = reviewerName,
    reviewerEmail = reviewerEmail
)

fun MetaDto.toDomain() = Meta(
    createdAt = createdAt,
    updatedAt = updatedAt,
    barcode = barcode,
    qrCode = qrCode
)
