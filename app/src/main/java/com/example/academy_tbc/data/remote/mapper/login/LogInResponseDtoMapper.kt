package com.example.academy_tbc.data.remote.mapper.login

import com.example.academy_tbc.data.remote.dto.response.login.LogInResponseDto
import com.example.academy_tbc.domain.common.ToDomainMapper
import com.example.academy_tbc.domain.model.login.AuthToken
import javax.inject.Inject

class LogInResponseDtoMapper @Inject constructor() : ToDomainMapper<LogInResponseDto, AuthToken> {

    override fun mapToDomain(data: LogInResponseDto): AuthToken {
        return AuthToken(
            token = data.token
        )
    }
}