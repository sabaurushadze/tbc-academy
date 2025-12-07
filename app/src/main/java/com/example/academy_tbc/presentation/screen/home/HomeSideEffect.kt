package com.example.academy_tbc.presentation.screen.home

import androidx.annotation.StringRes

sealed interface HomeSideEffect {
    data class ShowError(@param:StringRes val error: Int) : HomeSideEffect

}