package com.example.challenge.presentation.screen.log_in

data class LogInState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val accessToken: String = "",
)