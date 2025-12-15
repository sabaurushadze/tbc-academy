package com.example.academy_tbc.presentation.screen.part_detail

import com.example.academy_tbc.presentation.common.message.GenericString

sealed interface PartDetailSideEffect {
    data class ShowError(val error: GenericString) : PartDetailSideEffect
}