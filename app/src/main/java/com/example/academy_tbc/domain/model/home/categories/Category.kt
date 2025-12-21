package com.example.academy_tbc.domain.model.home.categories

data class Category(
    val id: Int,
    val categoryType: CategoryType,
    val title: String,
    val totalEvents: Int,
)
