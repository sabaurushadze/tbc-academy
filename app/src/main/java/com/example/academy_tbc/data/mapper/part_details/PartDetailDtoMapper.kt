package com.example.academy_tbc.data.mapper.part_details

import com.example.academy_tbc.data.model.response.part_details.PartDetailResponseDto
import com.example.academy_tbc.domain.model.part_details.PartDetail
import com.example.academy_tbc.domain.model.pc_parts.Condition

fun PartDetailResponseDto.toDomain(): PartDetail {
    return PartDetail(
        id = id,
        title = title,
        category = category,
        condition = Condition.fromString(condition),
        price = price,
        discount = discount,
        images = images,
        description = description,
        brand = brand,
        warranty = warranty,
        model = model,
        memorySize = memorySize,
        cores = cores
    )
}