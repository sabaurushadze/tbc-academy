package com.example.academy_tbc.data.mapper.categories

import com.example.academy_tbc.data.model.response.categories.CategoriesResponseDto
import com.example.academy_tbc.domain.model.categories.Category
import com.example.academy_tbc.domain.model.categories.PcPartCategory

fun CategoriesResponseDto.toDomain(): Category {
    return Category(
        id = id,
        category = PcPartCategory.fromInt(category),
        image = image
    )
}