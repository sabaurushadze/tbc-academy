package com.example.academy_tbc.presentation.screen.home.model

import androidx.annotation.StringRes

data class CheckboxOption(
    val id: String,
    @param:StringRes val labelRes: Int? = null,
    val label: String? = null,
    var isChecked: Boolean = false
)