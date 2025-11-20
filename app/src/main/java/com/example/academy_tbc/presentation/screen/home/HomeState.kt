package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.data.remote.home.UsersResponseDto

data class HomeState(
    val users: List<UsersResponseDto.User>? = null,
    val isLoading: Boolean = false,
    val error: String = "null"
)