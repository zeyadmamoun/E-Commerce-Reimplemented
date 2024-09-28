package com.example.e_commmercefixed.models.authModels

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val success: Boolean,
    val data: String
)
