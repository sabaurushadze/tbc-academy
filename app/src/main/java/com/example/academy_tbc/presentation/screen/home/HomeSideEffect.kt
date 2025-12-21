package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.util.GenericString

sealed interface HomeSideEffect {
    data class ShowError(val error: GenericString) : HomeSideEffect

}