package com.example.e_commmercefixed.repositories.data

import com.example.e_commmercefixed.models.products.Category
import com.example.e_commmercefixed.models.products.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductsRepositoryImpl(private val client: HttpClient) : ProductsRepository {
    override suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val httpResponse = client.get("https://api.escuelajs.co/api/v1/categories")
            var categories = httpResponse.body<List<Category>>()
            categories = categories.filter {
                it.name != "Testing Category"
            }
            Result(success = true,message = "Categories fetched successfully",data = categories)
        } catch (e: Exception) {
            Result(success = false, message = e.localizedMessage, data = null)
        }
    }
}