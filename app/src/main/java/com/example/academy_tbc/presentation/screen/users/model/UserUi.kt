package com.example.academy_tbc.presentation.screen.users.model

data class UserUi(
    val id: Int,
    val fullName: String,
    val email: String,
    val activationStatus: Int,
    val profileImageUrl: String?,
)