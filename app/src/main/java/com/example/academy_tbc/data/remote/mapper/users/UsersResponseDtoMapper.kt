package com.example.academy_tbc.data.remote.mapper.users

import com.example.academy_tbc.data.remote.dto.response.users.UsersResponseDto
import com.example.academy_tbc.domain.common.ToDomainMapper
import com.example.academy_tbc.domain.model.home.GetUsers
import javax.inject.Inject

class UsersResponseDtoMapper @Inject constructor() :
    ToDomainMapper<UsersResponseDto.UserModelDto, GetUsers.GetUser> {

    override fun mapToDomain(data: UsersResponseDto.UserModelDto): GetUsers.GetUser {
        return GetUsers.GetUser(
            id = data.id,
            email = data.email,
            firstName = data.firstName,
            lastName = data.lastName,
            avatar = data.avatar
        )
    }
}