package com.example.academy_tbc.presentation.screen.events.browse_events.categories.mapper

import com.example.academy_tbc.domain.model.home.categories.Category
import com.example.academy_tbc.presentation.screen.events.browse_events.categories.model.EventCategoryUi

fun Category.toEventCategoryUi() =
    EventCategoryUi(
        id = id,
        title = title,
    )