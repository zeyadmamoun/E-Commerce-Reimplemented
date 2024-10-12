package com.example.e_commmercefixed.models.products

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: Category,
    val images: List<String>
)
