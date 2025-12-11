package com.example.academy_tbc.domain.model.pc_parts

data class PcPartsQuery(
    val titleLike: String = "",
    val category: Int? = null,
    val minPrice: Float? = null,
    val maxPrice: Float? = null,
    val condition: String? = null,
    val sortBy: String? = null,
    val sortDescending: Boolean = false,
)
