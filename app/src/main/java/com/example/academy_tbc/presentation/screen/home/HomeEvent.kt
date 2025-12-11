package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery

sealed class HomeEvent {
    data object GetParts : HomeEvent()
    data class Search(val query: PcPartsQuery) : HomeEvent()
    data class GetPartsByCategory(val query: PcPartsQuery) : HomeEvent()
//    data object SignOut : HomeEvent()
}