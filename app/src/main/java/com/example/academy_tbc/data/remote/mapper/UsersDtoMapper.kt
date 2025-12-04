package com.example.academy_tbc.data.remote.mapper

import com.example.academy_tbc.data.local.room.users.UserEntity
import com.example.academy_tbc.data.remote.model.response.users.UsersResponseDto

fun UsersResponseDto.toEntity(): UserEntity =
    UserEntity(
        id = id,
        fullName = fullName,
        email = email,
        activationStatus = activationStatus,
        profileImageUrl = profileImageUrl
    )