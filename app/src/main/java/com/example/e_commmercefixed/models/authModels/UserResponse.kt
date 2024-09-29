package com.example.e_commmercefixed.models.authModels

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val success: Boolean = false,
    val userData: User? = null,
    val errorMessage: String = ""
)
