package com.example.academy_tbc.domain.usecase.categories

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.model.categories.Category
import com.example.academy_tbc.domain.repository.category.CategoryRepository
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(): Flow<Resource<List<Category>, ApiError>> {
        return categoryRepository.getCategories()
    }
}