package com.example.academy_tbc.data.mapper.network

import com.example.academy_tbc.data.model.response.register.RegisterResponseDto
import com.example.academy_tbc.domain.model.register.RegisterResponse

fun RegisterResponseDto.toDomain(): RegisterResponse {
    return RegisterResponse(
        id = id,
        token = token
    )
}