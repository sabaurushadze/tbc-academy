package com.example.academy_tbc.domain.model.part_details

import com.example.academy_tbc.domain.model.pc_parts.Condition

data class PartDetail(
    val id: Int,
    val title: String,
    val category: Int,
    val condition: Condition,
    val price: Float,
    val discount: Int,
    val images: List<String>,
    val description: String,
    val brand: String,
    val warranty: Int,
    val model: String,
    val memorySize: String?,
    val cores: String?,
)