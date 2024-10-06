package com.example.e_commmercefixed.fragments.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.repositories.authentication.AccountType
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class NavigationEvent{
    NavigateToHome,NavigateToWelcome
}

class SplashViewModel(private val repository: AuthRepository): ViewModel() {

    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private var _navigationEvent = MutableSharedFlow<NavigationEvent>(replay = 0)
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        val user = FirebaseAuth.getInstance().currentUser
        Log.i("FirebaseAuth",user.toString())
        if (user == null){
            viewModelScope.launch {
                val token = repository.token.first()
                Log.i("FirebaseAuth",token.toString())
                if (token.isNullOrEmpty()){
                    emitNavigationEvent(NavigationEvent.NavigateToWelcome)
                } else {
                    repository.currentUserType = AccountType.AppAccount
                    emitNavigationEvent(NavigationEvent.NavigateToHome)
                }
            }
        } else {
            repository.currentUserType = AccountType.GoogleAccount
            emitNavigationEvent(NavigationEvent.NavigateToHome)
        }
        _isLoading.update {
            false
        }
    }


    private fun emitNavigationEvent(event: NavigationEvent){
        viewModelScope.launch {
            _navigationEvent.emit(event)
        }
    }

}