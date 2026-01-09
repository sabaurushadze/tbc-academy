package com.example.challenge.presentation.screen.connection

import com.example.challenge.presentation.screen.connection.model.UiConnection

data class ConnectionState(
    val isLoading: Boolean = false,
    val connections: List<UiConnection>? = null,
    val errorMessage: String? = null,
)