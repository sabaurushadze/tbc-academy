package com.example.academy_tbc.data.remote.mapper.category

import com.example.academy_tbc.data.remote.dto.response.category.CategoryResponseDto
import com.example.academy_tbc.domain.model.category.Category

fun CategoryResponseDto.toDomain(): Category {
    return Category(
        id = id,
        name = name
    )
}