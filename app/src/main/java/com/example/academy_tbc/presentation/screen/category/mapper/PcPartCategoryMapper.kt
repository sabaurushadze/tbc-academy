package com.example.academy_tbc.presentation.screen.category.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.categories.PcPartCategory

fun PcPartCategory.toUiTextRes(): Int = when (this) {
    PcPartCategory.GPU -> R.string.gpu
    PcPartCategory.CPU -> R.string.cpu
    PcPartCategory.MOTHERBOARD -> R.string.motherboard
    PcPartCategory.RAM -> R.string.ram
    PcPartCategory.SSD -> R.string.ssd
    PcPartCategory.HDD -> R.string.hdd
    PcPartCategory.CPU_COOLER -> R.string.cpu_cooler
    PcPartCategory.PSU -> R.string.psu
    PcPartCategory.CASE_COOLER -> R.string.case_cooler
    PcPartCategory.CASE -> R.string.pc_case
}