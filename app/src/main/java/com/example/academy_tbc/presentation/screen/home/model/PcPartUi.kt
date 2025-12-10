package com.example.academy_tbc.presentation.screen.home.model

import androidx.annotation.StringRes

data class PcPartUi(
    val id: Int,
    val title: String,
    @param:StringRes val conditionTextRes: Int,
    val price: String,
    val priceBefore: String,
    val image: String,
    val hasDiscount: Boolean,
)