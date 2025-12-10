package com.example.academy_tbc.presentation.screen.category

sealed interface CategorySideEffect {
    data class ShowError(val message: String) : CategorySideEffect
}