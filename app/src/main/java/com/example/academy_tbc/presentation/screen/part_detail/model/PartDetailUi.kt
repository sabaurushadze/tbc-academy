package com.example.academy_tbc.presentation.screen.part_detail.model

data class PartDetailUi(
    val id: Int,
    val title: String,
    val category: Int,
    val condition: String,
    val price: Float,
    val discount: Int,
    val images: List<String>,
    val description: String,
    val brand: String,
    val warranty: Int,
    val model: String,
    val memorySize: String
)
