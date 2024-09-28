package com.example.e_commmercefixed.models.authModels

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserCredentials(
    val email: String,
    val password: String
)
