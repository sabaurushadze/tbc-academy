package com.example.academy_tbc.data.mapper.auth.sign_in

import com.example.academy_tbc.data.model.response.auth.sign_in.SignInResponseDto
import com.example.academy_tbc.domain.model.auth.sign_in.AuthToken

fun SignInResponseDto.toDomain() =
    AuthToken(token = token)
