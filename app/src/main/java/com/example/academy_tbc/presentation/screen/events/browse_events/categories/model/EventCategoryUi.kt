package com.example.academy_tbc.presentation.screen.events.browse_events.categories.model

import androidx.annotation.StringRes
import com.example.academy_tbc.domain.model.home.categories.CategoryType

data class EventCategoryUi(
    val id: Int,
    @param:StringRes val title: Int,
    val selected: Boolean = false,
)