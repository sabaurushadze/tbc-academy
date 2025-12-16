package com.example.academy_tbc.presentation.screen.part_detail

import com.example.academy_tbc.presentation.common.text.GenericString

sealed interface PartDetailSideEffect {
    data class ShowError(val error: GenericString) : PartDetailSideEffect
}