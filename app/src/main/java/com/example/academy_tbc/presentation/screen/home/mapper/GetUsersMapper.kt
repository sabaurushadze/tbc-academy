package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.domain.model.users.GetUsers
import com.example.academy_tbc.presentation.screen.home.model.UserModel

fun GetUsers.GetUser.toPresentation(): UserModel.User {
    return UserModel.User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}