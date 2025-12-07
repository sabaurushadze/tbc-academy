package com.example.academy_tbc.domain.model.pc_parts

data class PcPart(
    val id: Int,
    val title: String,
    val condition: Condition,
    val price: Float,
    val discount: Int,
    val image: String,
)