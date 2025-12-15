package com.example.academy_tbc.presentation.screen.profile

import com.example.academy_tbc.presentation.screen.profile.model.UserUi

data class ProfileState(
    val isLoading: Boolean = false,
    val user: UserUi? = null,
)