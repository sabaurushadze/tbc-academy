package com.example.academy_tbc.domain.repository.category

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.category.Category

interface CategoryRepository {
    suspend fun getCategories(): Resource<List<Category>, DataError.Network>
}