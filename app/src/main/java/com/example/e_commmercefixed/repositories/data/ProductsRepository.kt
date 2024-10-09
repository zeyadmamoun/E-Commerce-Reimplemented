package com.example.e_commmercefixed.repositories.data

import com.example.e_commmercefixed.models.products.Category
import com.example.e_commmercefixed.models.products.Result

interface ProductsRepository  {
    suspend fun getAllCategories(): Result<List<Category>>
}