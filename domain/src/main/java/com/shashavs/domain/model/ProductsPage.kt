package com.shashavs.domain.model

data class ProductsPage(
    val items: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
