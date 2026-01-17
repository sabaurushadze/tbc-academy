package com.example.academy_tbc.data.repository.category

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.mapper.category.toDomain
import com.example.academy_tbc.data.remote.service.category.CategoryService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.mapList
import com.example.academy_tbc.domain.model.category.Category
import com.example.academy_tbc.domain.repository.category.CategoryRepository
import javax.inject.Inject
class CategoryRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val categoryService: CategoryService,
) : CategoryRepository {
    override suspend fun getCategories(): Resource<List<Category>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            categoryService.getCategories()
        }.mapList {
            it.toDomain()
        }
    }
}