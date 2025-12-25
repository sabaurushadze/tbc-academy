package com.example.academy_tbc.data.service.categories

import com.example.academy_tbc.data.dto.response.home.categories.CategoriesResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET("/api/Events/categories")
    suspend fun getCategories(): Response<List<CategoriesResponseDto>>
}