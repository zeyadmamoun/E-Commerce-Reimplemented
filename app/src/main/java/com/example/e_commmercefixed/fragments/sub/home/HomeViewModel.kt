package com.example.e_commmercefixed.fragments.sub.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AuthRepository): ViewModel() {


    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}