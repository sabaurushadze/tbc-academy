package com.example.academy_tbc.presentation.screen.home.model

data class PcPartsQueryUi(
    val titleLike: String = "",
    val category: Int? = null,
    val minPrice: Float? = null,
    val maxPrice: Float? = null,
    val brand: String? = null,
    val condition: String? = null,
    val sortBy: String? = null,
    val sortDescending: Boolean = false,
)