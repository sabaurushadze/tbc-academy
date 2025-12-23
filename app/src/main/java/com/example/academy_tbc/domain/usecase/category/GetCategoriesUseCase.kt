package com.example.academy_tbc.domain.usecase.category

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import com.example.academy_tbc.domain.model.home.categories.Category
import com.example.academy_tbc.domain.repository.categories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(): Flow<Resource<List<Category>, ApiError>> {
        return categoryRepository.getCategories()
    }
}