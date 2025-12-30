package com.example.academy_tbc.presentation.screen.categories

import com.example.academy_tbc.presentation.screen.categories.model.GetEquipment

data class CategoryState(
    val isLoading: Boolean = false,
    val equipments: List<GetEquipment>? = null,
)