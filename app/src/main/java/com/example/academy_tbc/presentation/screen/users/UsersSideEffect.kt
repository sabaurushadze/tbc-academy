package com.example.academy_tbc.presentation.screen.users

import androidx.annotation.StringRes

sealed interface UsersSideEffect {
    data class ShowError(@param:StringRes val error: Int) : UsersSideEffect
    data object ShowOffline : UsersSideEffect
    data object ShowOnline : UsersSideEffect
}