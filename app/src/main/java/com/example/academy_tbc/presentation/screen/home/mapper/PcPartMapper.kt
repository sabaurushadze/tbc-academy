package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.presentation.screen.home.model.PcPartUi

fun PcPart.toUi(finalPrice: Float): PcPartUi {
    return PcPartUi(
        id = id,
        title = title,
        conditionTextRes = condition.toUiTextRes(),
        price = "%.2f".format(finalPrice),
        priceBefore = "%.2f".format(price),
        images = images,
        hasDiscount = discount > 0
    )
}