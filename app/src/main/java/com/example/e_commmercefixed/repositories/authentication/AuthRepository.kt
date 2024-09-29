package com.example.e_commmercefixed.repositories.authentication

import com.example.e_commmercefixed.models.authModels.LoginUserCredentials
import com.example.e_commmercefixed.models.authModels.Response
import com.example.e_commmercefixed.models.authModels.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val token: Flow<String?>
    suspend fun fastLogin(token: String): Response
    suspend fun login(userCredentials: LoginUserCredentials): Response
    suspend fun signup(user: User): Response
    suspend fun logout()
    suspend fun saveLoginToken(token: String)
}