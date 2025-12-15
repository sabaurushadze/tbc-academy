package com.example.academy_tbc.presentation.screen.profile.mapper

import com.example.academy_tbc.domain.model.auth.User
import com.example.academy_tbc.presentation.screen.profile.model.UserUi

fun User.toUi(): UserUi {
    return UserUi(
        id = id,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl
    )
}