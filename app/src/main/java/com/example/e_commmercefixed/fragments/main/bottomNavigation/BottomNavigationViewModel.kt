package com.example.e_commmercefixed.fragments.main.bottomNavigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BottomNavigationViewModel(
    private val repository: AuthRepository
): ViewModel() {

    private lateinit var token: String
    fun getUserData(){
        viewModelScope.launch{
            token = repository.token.first().toString()
            val userData = repository.getUserData(token)
            Log.i("UserResponseError",userData.toString())
        }
    }
}