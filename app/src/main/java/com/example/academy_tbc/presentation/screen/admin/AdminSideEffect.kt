package com.example.academy_tbc.presentation.screen.admin

import androidx.annotation.StringRes

sealed interface AdminSideEffect {
    data class ShowError(@StringRes val errorRes: Int) : AdminSideEffect
    data object Success : AdminSideEffect
}