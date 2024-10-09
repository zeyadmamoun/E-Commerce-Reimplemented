package com.example.e_commmercefixed.fragments.sub.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.models.products.Category
import com.example.e_commmercefixed.repositories.authentication.AuthRepository
import com.example.e_commmercefixed.repositories.data.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val productsRepository: ProductsRepository
): ViewModel() {

    private var _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()


    fun getCategories(){
        viewModelScope.launch {
            val response = productsRepository.getAllCategories()
            if (response.success && response.data != null){
                Log.i("HomeViewModel", response.data.toString())
                _categories.update {
                    response.data
                }
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}