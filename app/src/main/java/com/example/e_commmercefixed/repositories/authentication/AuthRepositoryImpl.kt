package com.example.e_commmercefixed.repositories.authentication

import android.util.Log
import com.example.e_commmercefixed.models.authModels.LoginUserCredentials
import com.example.e_commmercefixed.models.authModels.Response
import com.example.e_commmercefixed.models.authModels.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepositoryImpl(private val client: HttpClient) : AuthRepository {

    override suspend fun fastLogin(token: String): Response {
        TODO("Not yet implemented")
    }

    override suspend fun login(userCredentials: LoginUserCredentials): Response {
        return try {
            val httpResponse = client.post("http://192.168.1.18:8080/login") {
                contentType(ContentType.Application.Json)
                setBody(userCredentials)
            }
            val response: Response = httpResponse.body()
            response
        } catch (e: Exception) {
            Log.d("AuthRepo", e.message.toString())
            Response(false, e.message.toString())
        }
    }

    override suspend fun signup(user: User): Response {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}