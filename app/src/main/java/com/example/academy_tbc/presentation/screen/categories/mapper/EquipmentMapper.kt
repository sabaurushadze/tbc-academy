package com.example.academy_tbc.presentation.screen.categories.mapper

import com.example.academy_tbc.domain.model.equipment.Equipment
import com.example.academy_tbc.presentation.screen.categories.model.GetEquipment

fun Equipment.toPresentation(depth: Int = 0): List<GetEquipment> {
    val self = GetEquipment(
        id = id,
        name = name,
        depth = depth.coerceAtMost(4)
    )

    return listOf(self) + children.flatMap {
        it.toPresentation(depth + 1)
    }
}
