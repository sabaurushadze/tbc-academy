package com.example.academy_tbc.data.remote.mapper

import com.example.academy_tbc.data.remote.model.response.login.LogInResponseDto
import com.example.academy_tbc.domain.model.login.LogInResponse

fun LogInResponseDto.toDomain(): LogInResponse {
    return LogInResponse(
        token = token
    )
}