package com.example.academy_tbc.presentation.screen.users

import com.example.academy_tbc.presentation.screen.users.model.UserUi

data class UsersState(
    val isLoading: Boolean = false,
    val users: List<UserUi>? = null
)