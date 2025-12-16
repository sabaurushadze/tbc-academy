package com.example.academy_tbc.presentation.screen.part_detail.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.presentation.screen.part_detail.model.ItemDetailUi
import com.example.academy_tbc.presentation.screen.part_detail.model.PartDetailUi

fun getItemDetailsByCategory(part: PartDetailUi): List<ItemDetailUi> {
    return when (part.category) {
        //GPU
        1 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.memory_size, value = part.memorySize),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //CPU
        2 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.memory_size, value = part.memorySize),
            ItemDetailUi(R.string.cores, part.cores),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //MOTHERBOARD
        3 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //RAM
        4 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //SSD
        5 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //PSU
        6 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //CPU COOLER
        7 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //HDD
        8 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //CASE COOLER
        9 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )
        //CASE
        10 -> listOf(
            ItemDetailUi(R.string.brand, value = part.brand),
            ItemDetailUi(R.string.model, value = part.model),
            ItemDetailUi(
                titleRes = R.string.warranty,
                valueInt = part.warranty,
                valueRes = R.string.months
            )
        )

        else -> emptyList()
    }
}