package com.example.academy_tbc.presentation.screen.categories

sealed class CategoryEvent {
    data class FetchEquipmentEvent(val filteredName: String = "") : CategoryEvent()
}