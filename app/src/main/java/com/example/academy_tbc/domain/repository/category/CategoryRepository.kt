package com.example.academy_tbc.domain.repository.category

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.model.categories.Category
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<Resource<List<Category>, ApiError>>
}