package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.common.text.GenericString

sealed interface HomeSideEffect {
    data class ShowError(val error: GenericString) : HomeSideEffect
}