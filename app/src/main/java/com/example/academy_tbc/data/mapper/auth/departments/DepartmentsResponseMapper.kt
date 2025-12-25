package com.example.academy_tbc.data.mapper.auth.departments

import com.example.academy_tbc.data.dto.response.auth.departments.DepartmentsResponseDto
import com.example.academy_tbc.domain.model.auth.departments.Department

fun DepartmentsResponseDto.toDomain() =
    Department(
        id = id,
        name = name
    )

fun List<DepartmentsResponseDto>.toDomain() = map { it.toDomain() }