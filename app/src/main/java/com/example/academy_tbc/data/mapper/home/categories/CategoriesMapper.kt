package com.example.academy_tbc.data.mapper.home.categories

import com.example.academy_tbc.data.model.response.home.categories.CategoriesResponseDto
import com.example.academy_tbc.domain.model.home.categories.Category

fun CategoriesResponseDto.toDomain() =
    Category(
        id = id,
        title = title,
        totalEvents = totalEvents
    )