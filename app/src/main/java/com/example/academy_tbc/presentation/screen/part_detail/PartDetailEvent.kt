package com.example.academy_tbc.presentation.screen.part_detail

sealed class PartDetailEvent {
    data class GetPartDetails(val id: Int) : PartDetailEvent()
}