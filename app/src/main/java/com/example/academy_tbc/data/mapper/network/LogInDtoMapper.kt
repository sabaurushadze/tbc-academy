package com.example.academy_tbc.data.mapper.network

import com.example.academy_tbc.data.model.response.login.LogInResponseDto
import com.example.academy_tbc.domain.model.login.LogInResponse

fun LogInResponseDto.toDomain(): LogInResponse {
    return LogInResponse(
        token = token
    )
}