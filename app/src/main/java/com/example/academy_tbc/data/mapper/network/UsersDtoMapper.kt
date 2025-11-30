package com.example.academy_tbc.data.mapper.network

import com.example.academy_tbc.data.model.response.users.UsersResponseDto
import com.example.academy_tbc.domain.model.users.GetUsers

fun UsersResponseDto.toDomain(): GetUsers {
    return GetUsers(
        page = page,
        perPage = perPage,
        total = total,
        totalPages = totalPages,
        data = data.map { user ->
            GetUsers.GetUser(
                id = user.id,
                email = user.email,
                firstName = user.firstName,
                lastName = user.lastName,
                avatar = user.avatar
            )
        }
    )
}
