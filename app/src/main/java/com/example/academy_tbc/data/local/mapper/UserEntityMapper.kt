package com.example.academy_tbc.data.local.mapper

import com.example.academy_tbc.data.local.room.users.UserEntity
import com.example.academy_tbc.domain.model.users.User

fun UserEntity.toDomain(): User =
    User(
        id = id,
        fullName = fullName,
        email = email,
        activationStatus = activationStatus.toActivationStatus(),
        profileImageUrl = profileImageUrl
    )