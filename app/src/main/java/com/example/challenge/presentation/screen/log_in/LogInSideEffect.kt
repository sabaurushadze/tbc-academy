package com.example.challenge.presentation.screen.log_in

sealed interface LogInSideEffect {
    data object NavigateToConnections : LogInSideEffect
}
