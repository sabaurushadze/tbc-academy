package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.users.model.UiUser

data class HomeState(
    val isLoading: Boolean = false,
    val users: List<UiUser> = listOf(),
    val query: String = "",
)