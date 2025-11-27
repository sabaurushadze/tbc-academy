package com.example.academy_tbc.presentation.screen.admin

sealed class AdminEvent {
    data class SaveUser(val firstName: String, val lastName: String, val email: String) : AdminEvent()
}