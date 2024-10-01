package com.example.e_commmercefixed.fragments.main.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.models.authModels.Response
import com.example.e_commmercefixed.models.authModels.User
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class SignupResult {
    data class Success(val token: String) : SignupResult()
    data class Failure(val messageError: String) : SignupResult()
}

data class SignupUiState(
    val signupError: String = "",
    val isLoading: Boolean = false
)


class SignupViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    private var _navigationEvent = MutableSharedFlow<Unit>(replay = 0)
    val navigationEvent: SharedFlow<Unit> = _navigationEvent

    fun signup(firstName: String, lastName: String, email: String, password: String) {
        val user = User(firstName, lastName, email, password)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setLoading(true)
                val result = performSignup(user)
                handleSignupResult(result)
            } catch (e: Exception) {
                Log.i("LoginViewModel", e.toString())
            } finally {
                setLoading(false)
            }
        }
    }

    private suspend fun performSignup(user: User): SignupResult {
        return try {
            val response = repository.signup(user)
            generateResultDueToResponse(response)
        } catch (e: Exception) {
            SignupResult.Failure(e.message.toString())
        }
    }

    private fun generateResultDueToResponse(response: Response): SignupResult {
        return when (response.success) {
            true -> SignupResult.Success(response.data)
            else -> SignupResult.Failure(response.data)
        }
    }

    private suspend fun handleSignupResult(result: SignupResult) {
        when (result) {
            is SignupResult.Success -> {
                updateUiState(SignupUiState(""))
                repository.saveLoginToken(result.token)
                navigateToHomeScreen()
            }

            is SignupResult.Failure -> updateUiState(SignupUiState(result.messageError))

        }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    private fun updateUiState(newState: SignupUiState) {
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