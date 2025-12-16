package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.model.PcPartsQueryUi

sealed class HomeEvent {
    data object GetParts : HomeEvent()
    data class Search(val query: PcPartsQueryUi) : HomeEvent()
    data class GetPartsByCategory(val query: PcPartsQueryUi) : HomeEvent()
    data class SaveCategory(val category: Int) : HomeEvent()
    data object GetCategories : HomeEvent()

}