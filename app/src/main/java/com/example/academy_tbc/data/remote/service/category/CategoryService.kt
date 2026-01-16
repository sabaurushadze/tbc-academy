package com.example.academy_tbc.data.remote.service.category

import com.example.academy_tbc.data.remote.dto.response.category.CategoryResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET(CATEGORIES)
    suspend fun getCategories(): Response<List<CategoryResponseDto>>

    companion object {
        private const val CATEGORIES = "categories"
    }
}