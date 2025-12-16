package com.example.academy_tbc.presentation.screen.part_detail.model

import androidx.annotation.StringRes

data class ItemDetailUi(
    @param:StringRes val titleRes: Int,
    val value: String? = null,
    val valueInt: Int? = null,
    @param:StringRes val valueRes: Int? = null
)