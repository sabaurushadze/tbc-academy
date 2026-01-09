package com.example.challenge.presentation.screen.connection.model

data class UiConnection(
    val avatar: String?,
    val email: String,
    val id: Int,
    val fullName: String,
    val isSelected: Boolean = false
)