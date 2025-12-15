package com.example.academy_tbc.presentation.screen.part_detail.mapper

import com.example.academy_tbc.domain.model.part_details.PartDetail
import com.example.academy_tbc.presentation.screen.part_detail.model.PartDetailUi

fun PartDetail.toUi(): PartDetailUi {
    return PartDetailUi(
        id = id,
        title = title,
        category = category,
        condition = condition,
        price = price,
        discount = discount,
        images = images,
        description = description,
        brand = brand,
        warranty = warranty,
        model = model,
        memorySize = memorySize
    )
}