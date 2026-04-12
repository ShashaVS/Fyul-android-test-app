package com.shashavs.fyultest.ui.models

import com.shashavs.domain.model.Product

data class ProductUI(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String
)

fun Product.toUI() = ProductUI(
    id = id,
    title = title,
    description = description,
    thumbnail = thumbnail
)
