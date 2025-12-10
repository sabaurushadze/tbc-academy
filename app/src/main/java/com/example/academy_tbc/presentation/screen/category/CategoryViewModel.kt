package com.example.academy_tbc.presentation.screen.category

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.domain.usecase.categories.GetCategoriesUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.category.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel<CategoryState, CategorySideEffect, CategoryEvent>(CategoryState()) {

    init {
        getCategories()
    }

    override fun onEvent(event: CategoryEvent) {
        when (event) {

            else -> {}
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        updateState { copy(categories = result.data.map { it.toUi() }) }
                    }

                    is Resource.Error -> sendEffect(CategorySideEffect.ShowError(result.error))
                }
            }
        }
    }


}