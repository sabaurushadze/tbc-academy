package com.example.academy_tbc.data.dto.response.auth.departments

import kotlinx.serialization.Serializable

@Serializable
data class DepartmentsResponseDto(
    val id: Int,
    val name: String,
)