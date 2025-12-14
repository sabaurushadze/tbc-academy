package com.example.academy_tbc.data.repository.category

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.mapper.categories.toDomain
import com.example.academy_tbc.data.service.categories.CategoriesService
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.categories.Category
import com.example.academy_tbc.domain.repository.category.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val api: CategoriesService,
) : CategoryRepository {
    override fun getCategories(): Flow<Resource<List<Category>, ApiError>> {
        return responseHandler.safeCall {
            api.getCategories()
        }.mapResource {
            it.toDomain()
        }
    }
}