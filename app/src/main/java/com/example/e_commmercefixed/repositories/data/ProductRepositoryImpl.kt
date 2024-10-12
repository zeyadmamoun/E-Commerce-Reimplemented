package com.example.e_commmercefixed.repositories.data

import com.example.e_commmercefixed.models.products.Category
import com.example.e_commmercefixed.models.products.Product
import com.example.e_commmercefixed.models.products.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ProductsRepositoryImpl(private val client: HttpClient) : ProductsRepository {
    override suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val httpResponse = client.get("https://api.escuelajs.co/api/v1/categories")
            var categories = httpResponse.body<List<Category>>()
            categories = categories.filter {
                it.id < 6 && it.name != "Testing Category"
            }
            Result(success = true,message = "Categories fetched successfully",data = categories)
        } catch (e: Exception) {
            Result(success = false, message = e.localizedMessage, data = null)
        }
    }

    override suspend fun getProducts(offset: Int?, limit: Int): Result<List<Product>> {
        return try {
            val response: HttpResponse = client.get("https://api.escuelajs.co/api/v1/products") {
                offset?.let {
                    url {
                        parameters.append("offset",offset.toString())
                        parameters.append("limit",limit.toString())
                    }
                }
            }
            val data: List<Product> = response.body()
            Result(success = true, data = data, message = "data fetched successfully")
        }catch (e: Exception){
            Result(success = false, data = null, message = "data fetched successfully")
        }
    }
}