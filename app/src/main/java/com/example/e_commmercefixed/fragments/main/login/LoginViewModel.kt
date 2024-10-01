package com.example.e_commmercefixed.fragments.main.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.models.authModels.LoginUserCredentials
import com.example.e_commmercefixed.models.authModels.Response
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class LoginResult {
    data class Success(val token: String) : LoginResult()
    data class EmailError(val message: String) : LoginResult()
    data class PasswordError(val message: String) : LoginResult()
    data class UnknownError(val message: String) : LoginResult()
}

data class LoginUiState(
    val emailError: String = "",
    val passwordError: String = "",
    val isLoading: Boolean = false,
)

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private var _navigationEvent = MutableSharedFlow<Unit>(replay = 0)
    val navigationEvent: SharedFlow<Unit> = _navigationEvent

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            try {
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
            response.success -> LoginResult.Success(response.data)
            response.data == "Email not found" -> LoginResult.EmailError(response.data)
            response.data == "invalid email or password" -> LoginResult.PasswordError(response.data)
            else -> LoginResult.UnknownError("Unknown error: ${response.data}")
        }
    }

    private fun handleLoginResult(result: LoginResult) {
        when (result) {
            is LoginResult.Success -> {
                updateUiState(LoginUiState())
                viewModelScope.launch {
                    repository.saveLoginToken(result.token)
                }
                navigateToHomeScreen()
            }
            is LoginResult.EmailError -> updateUiState(LoginUiState(emailError = result.message))
            is LoginResult.PasswordError -> updateUiState(LoginUiState(passwordError = result.message))
            is LoginResult.UnknownError -> updateUiState(LoginUiState(result.message))
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    private fun updateUiState(newState: LoginUiState) {
        _uiState.update {
            newState
        }
    }

    private fun navigateToHomeScreen() {
        viewModelScope.launch {
            _navigationEvent.emit(Unit)
        }
    }
}