package com.example.academy_tbc.presentation.screen.part_detail.mapper

import com.example.academy_tbc.presentation.screen.part_detail.model.ItemDetailUi
import com.example.academy_tbc.presentation.screen.part_detail.model.PartDetailUi



fun getItemDetailsByCategory(part: PartDetailUi): List<ItemDetailUi> {
    return when (part.category) {
//      GPU
        1 -> listOf(
            ItemDetailUi("Brand", part.brand),
            ItemDetailUi("Memory Size", part.memorySize),
            ItemDetailUi("Model", part.model),
            ItemDetailUi("Warranty", "${part.warranty} months")
        )
//      CPU
        2 -> listOf(
            ItemDetailUi("Brand", part.brand),
            ItemDetailUi("Model", part.model),
        )
        else -> emptyList()
    }
}