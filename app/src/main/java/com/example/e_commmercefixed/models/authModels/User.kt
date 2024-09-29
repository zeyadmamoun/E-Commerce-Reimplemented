package com.example.e_commmercefixed.models.authModels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("firstname") val firstName: String,
    @SerialName("lastname") val lastName: String,
    val email: String,
    val password: String,
)
