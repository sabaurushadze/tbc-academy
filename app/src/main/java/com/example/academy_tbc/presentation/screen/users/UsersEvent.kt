package com.example.academy_tbc.presentation.screen.users

import com.example.academy_tbc.presentation.screen.users.model.UserUi

sealed class UsersEvent {
    data class DeleteUser(val user: UserUi) : UsersEvent()
}