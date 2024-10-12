package com.example.e_commmercefixed.fragments.sub.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commmercefixed.models.products.Category
import com.example.e_commmercefixed.models.products.Product
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

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    fun getCategories(){
        viewModelScope.launch {
            val response = productsRepository.getAllCategories()
            if (response.success && response.data != null){
                Log.i("HomeViewModel", response.data.toString())
                _uiState.update {
                    it.copy(
                        categories = response.data
                    )
                }
            }
        }
    }

    fun getProducts(){
        viewModelScope.launch {
            val response = productsRepository.getProducts(0,8)
            if (response.success && response.data != null){
                Log.i("HomeViewModel", response.data.toString())
                Log.i("HomeViewModel", response.data.size.toString())
                _uiState.update {
                    it.copy(
                        products = response.data
                    )
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

data class HomeUiState(
    val categories: List<Category> = emptyList(),
    val products: List<Product> = emptyList()
)