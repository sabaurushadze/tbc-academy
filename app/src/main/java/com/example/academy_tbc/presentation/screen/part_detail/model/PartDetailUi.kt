package com.example.academy_tbc.presentation.screen.part_detail.model

import androidx.annotation.StringRes

data class PartDetailUi(
    val id: Int,
    val title: String,
    val category: Int,
    @param:StringRes val condition: Int,
    val price: String,
    val discount: Int,
    val images: List<String>,
    val description: String,
    val brand: String,
    val warranty: Int,
    val model: String,
    val memorySize: String?,
    val cores: String?,
)
