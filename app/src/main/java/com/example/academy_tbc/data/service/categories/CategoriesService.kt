package com.example.academy_tbc.data.service.categories

import com.example.academy_tbc.data.model.response.categories.CategoriesResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesService {
    @GET("categories")
    suspend fun getCategories(): Response<List<CategoriesResponseDto>>
}