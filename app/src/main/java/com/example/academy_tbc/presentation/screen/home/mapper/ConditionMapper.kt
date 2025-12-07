package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.pc_parts.Condition

fun Condition.toUiTextRes(): Int = when (this) {
    Condition.NEW -> R.string.condition_new
    Condition.OPEN_BOX -> R.string.condition_open_box
    Condition.PRE_OWNED -> R.string.condition_pre_owned
}