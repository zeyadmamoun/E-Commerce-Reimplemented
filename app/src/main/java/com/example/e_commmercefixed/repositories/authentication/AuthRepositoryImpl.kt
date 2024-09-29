package com.example.e_commmercefixed.repositories.authentication

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.e_commmercefixed.models.authModels.LoginUserCredentials
import com.example.e_commmercefixed.models.authModels.Response
import com.example.e_commmercefixed.models.authModels.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    override val token: Flow<String?> = dataStore.data
        .catch {
            if (it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }else{
                throw it
            }
        }
        .map {preferences ->
        preferences[LOGIN_TOKEN]
    }

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

    override suspend fun saveLoginToken(token: String) {
        dataStore.edit { preferences ->
            preferences[LOGIN_TOKEN] = token
        }
    }

    private companion object{
        val LOGIN_TOKEN =  stringPreferencesKey("user_token")
        const val TAG = "AuthRepositoryImpl"
    }
}