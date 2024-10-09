package com.example.e_commmercefixed.models.products

data class Result<T>(
    val success: Boolean,     // To indicate if the operation was successful
    val message: String?,     // To provide a message (error/success)
    val data: T? = null       // Generic data that can be of any type
)