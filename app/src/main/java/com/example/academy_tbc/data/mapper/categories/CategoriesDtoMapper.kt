package com.example.academy_tbc.data.mapper.categories

import com.example.academy_tbc.data.model.response.categories.CategoriesResponseDto
import com.example.academy_tbc.domain.model.categories.Category

fun CategoriesResponseDto.toDomain(): Category {
    return Category(
        id = id,
        category = category,
        image = image
    )
}