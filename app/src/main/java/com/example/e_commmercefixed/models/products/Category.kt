package com.example.e_commmercefixed.models.products

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val image: String
)