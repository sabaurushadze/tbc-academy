package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery
import com.example.academy_tbc.presentation.screen.home.model.PcPartsQueryUi


fun PcPartsQueryUi.toDomain(): PcPartsQuery {
    return PcPartsQuery(
        titleLike = titleLike,
        category = category,
        minPrice = minPrice,
        maxPrice = maxPrice,
        brand = brand,
        filters = filters,
        condition = condition,
        sortBy = sortBy,
        sortDescending = sortDescending
    )
}