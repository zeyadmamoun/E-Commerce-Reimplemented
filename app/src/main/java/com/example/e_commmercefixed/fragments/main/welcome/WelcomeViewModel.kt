package com.example.e_commmercefixed.fragments.main.welcome

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.repositories.authentication.AccountType
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(private val repository: AuthRepository) : ViewModel() {

    private var _signInState: MutableSharedFlow<Boolean> = MutableSharedFlow(replay = 0)
    val signInState: SharedFlow<Boolean> = _signInState

    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                viewModelScope.launch {
                    repository.currentUserType = AccountType.GoogleAccount
                    _signInState.emit(true)
                }
            }
        } else {
            if (response == null) {
                viewModelScope.launch {
                    _signInState.emit(false)
                }
            } else {
                val errorCode = response.error?.errorCode
                Log.i("FirebaseAuth",errorCode.toString())
            }
        }
    }
}