package com.example.academy_tbc.data.dto.request.auth.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class DepartmentsRequestDto(
    val departmentName: String,
    val departmentCode: Int
)