package com.example.academy_tbc.presentation.screen.category

import com.example.academy_tbc.presentation.screen.category.model.CategoryUi


data class CategoryState(
    val isLoading: Boolean = false,
    val categories: List<CategoryUi> = listOf()
)