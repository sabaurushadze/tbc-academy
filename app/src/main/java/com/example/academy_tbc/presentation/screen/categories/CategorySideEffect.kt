package com.example.academy_tbc.presentation.screen.categories

import com.example.academy_tbc.presentation.util.GenericString

sealed interface CategorySideEffect {
    data object NavigateToHome : CategorySideEffect
    data class ShowError(val error: GenericString) : CategorySideEffect

}