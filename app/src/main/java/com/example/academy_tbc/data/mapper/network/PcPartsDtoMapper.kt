package com.example.academy_tbc.data.mapper.network

import com.example.academy_tbc.data.model.response.pc_parts.PcPartsResponseDto
import com.example.academy_tbc.domain.model.pc_parts.Condition
import com.example.academy_tbc.domain.model.pc_parts.PcPart

fun PcPartsResponseDto.toDomain(): PcPart {
    return PcPart(
        id = id,
        title = title,
        condition = Condition.fromString(condition),
        price = price,
        discount = discount,
        image = image
    )
}