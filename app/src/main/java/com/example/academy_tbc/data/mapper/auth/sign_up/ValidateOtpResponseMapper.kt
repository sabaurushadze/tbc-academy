package com.example.academy_tbc.data.mapper.auth.sign_up

import com.example.academy_tbc.data.dto.response.auth.sign_up.ValidateOtpResponseDto
import com.example.academy_tbc.domain.model.auth.sign_up.OptValidationResult

fun ValidateOtpResponseDto.toDomain() =
    OptValidationResult(
        isValid = isValid
    )