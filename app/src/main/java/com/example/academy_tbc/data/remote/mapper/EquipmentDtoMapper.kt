package com.example.academy_tbc.data.remote.mapper

import com.example.academy_tbc.data.remote.dto.response.equipment.EquipmentResponseDto
import com.example.academy_tbc.domain.model.equipment.Equipment

fun EquipmentResponseDto.toDomain(): Equipment =
    Equipment(
        id = id,
        name = name,
        nameDe = nameDe,
        createdAt = createdAt,
        bglNumber = bglNumber,
        bglVariant = bglVariant,
        orderId = orderId,
        main = main,
        children = children.map { it.toDomain() }
    )

fun List<EquipmentResponseDto>.toDomain() = this.map { it.toDomain() }