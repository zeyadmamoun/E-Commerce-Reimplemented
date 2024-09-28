package com.example.e_commmercefixed.fragments.main.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.models.authModels.LoginUserCredentials
import com.example.e_commmercefixed.models.authModels.Response
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class LoginResult {
    object Success : LoginResult()
    data class EmailError(val message: String) : LoginResult()
    data class PasswordError(val message: String) : LoginResult()
    data class UnknownError(val message: String) : LoginResult()
}

data class LoginUiState(
    val emailError: String = "",
    val passwordError: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false
)

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setLoading(true)
                val result = performLogin(email, password)
                handleLoginResult(result)
            } catch (e: Exception) {
                Log.i("LoginViewModel", e.toString())
            } finally {
                setLoading(false)
            }
        }
    }

    private suspend fun performLogin(email: String, password: String): LoginResult {
        return try {
            val user = LoginUserCredentials(email, password)
            val response = repository.login(user)
            generateResultDueToResponse(response)
        } catch (e: Exception) {
            LoginResult.UnknownError(e.message ?: "An unknown error occurred")
        }
    }

    private fun generateResultDueToResponse(response: Response): LoginResult {
        return when {
            response.success -> LoginResult.Success
            response.data == "Email not found" -> LoginResult.EmailError(response.data)
            response.data == "invalid email or password" -> LoginResult.PasswordError(response.data)
            else -> LoginResult.UnknownError("Unknown error: ${response.data}")
        }
    }

    private fun handleLoginResult(result: LoginResult) {
        _uiState.update { currentState ->
            when (result) {
                is LoginResult.Success -> currentState.copy(emailError = "", passwordError = "", isLoginSuccess = true)
                is LoginResult.EmailError -> currentState.copy(emailError = result.message, passwordError = "")
                is LoginResult.PasswordError -> currentState.copy(emailError = "", passwordError = result.message)
                is LoginResult.UnknownError -> currentState.copy(emailError = result.message, passwordError = result.message)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }
}