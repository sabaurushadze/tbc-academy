package com.example.academy_tbc.data.repository.categories

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.mapper.home.categories.toDomain
import com.example.academy_tbc.data.service.categories.CategoryService
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import com.example.academy_tbc.domain.model.home.categories.Category
import com.example.academy_tbc.domain.repository.categories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryService,
    private val responseHandler: ApiResponseHandler,
) : CategoryRepository {
    override fun getCategories(): Flow<Resource<List<Category>, ApiError>> {
        return responseHandler.safeApiCall {
            api.getCategories()
        }.mapResource {
            it.toDomain()
        }
    }
}