package com.example.academy_tbc.data.mapper.network

import com.example.academy_tbc.data.model.response.users.UsersResponseDto
import com.example.academy_tbc.domain.model.users.GetUsers

fun UsersResponseDto.UserModelDto.toDomain(): GetUsers.GetUser {
    return GetUsers.GetUser(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}
