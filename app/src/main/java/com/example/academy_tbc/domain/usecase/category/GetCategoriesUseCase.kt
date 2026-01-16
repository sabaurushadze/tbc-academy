package com.example.academy_tbc.domain.usecase.category

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.category.Category
import com.example.academy_tbc.domain.repository.category.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Resource<List<Category>, DataError.Network> {
        return categoryRepository.getCategories()
    }
}