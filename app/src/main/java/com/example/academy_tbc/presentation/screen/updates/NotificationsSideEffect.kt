package com.example.academy_tbc.presentation.screen.updates

import com.example.academy_tbc.presentation.util.GenericString

sealed interface NotificationsSideEffect {
    data class ShowError(val error: GenericString) : NotificationsSideEffect
}