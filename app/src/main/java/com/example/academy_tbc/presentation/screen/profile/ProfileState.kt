package com.example.academy_tbc.presentation.screen.profile

data class ProfileState(
    val isLoading: Boolean = false,
    val email: String = "",
    val fullName: String = "",
    val department: String = ""
)