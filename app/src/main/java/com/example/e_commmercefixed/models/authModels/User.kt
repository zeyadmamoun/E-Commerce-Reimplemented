package com.example.e_commmercefixed.models.authModels

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
