package com.example.academy_tbc.presentation.screen.home

import androidx.annotation.StringRes

sealed interface HomeSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : HomeSideEffect

}