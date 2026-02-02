package com.tbc.home.presentation.screen.home

import androidx.annotation.StringRes

sealed interface HomeSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : HomeSideEffect

}