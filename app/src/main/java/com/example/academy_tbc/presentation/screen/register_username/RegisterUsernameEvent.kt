package com.example.academy_tbc.presentation.screen.register_username

sealed class RegisterUsernameEvent {
    data object Register : RegisterUsernameEvent()
    data class UsernameChanged(val username: String) : RegisterUsernameEvent()
}