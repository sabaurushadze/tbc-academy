package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.common.Constants.ASUS
import com.example.academy_tbc.domain.common.Constants.BRAND
import com.example.academy_tbc.domain.common.Constants.CONDITION
import com.example.academy_tbc.domain.common.Constants.I3
import com.example.academy_tbc.domain.common.Constants.I5
import com.example.academy_tbc.domain.common.Constants.I7
import com.example.academy_tbc.domain.common.Constants.I9
import com.example.academy_tbc.domain.common.Constants.INTEL_CORE_I3
import com.example.academy_tbc.domain.common.Constants.INTEL_CORE_I5
import com.example.academy_tbc.domain.common.Constants.INTEL_CORE_I7
import com.example.academy_tbc.domain.common.Constants.INTEL_CORE_I9
import com.example.academy_tbc.domain.common.Constants.MODEL
import com.example.academy_tbc.domain.common.Constants.MSI
import com.example.academy_tbc.domain.common.Constants.NEW
import com.example.academy_tbc.domain.common.Constants.OPEN_BOX
import com.example.academy_tbc.domain.common.Constants.PRE_OWNED
import com.example.academy_tbc.presentation.screen.home.model.CheckboxOption
import com.example.academy_tbc.presentation.screen.home.model.FilterUi

fun getFiltersByCategory(category: Int): List<FilterUi> {
    return when (category) {

        1 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        2 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = MODEL,
                titleRes = R.string.model,
                options = listOf(
                    CheckboxOption(id = I3, label = INTEL_CORE_I3),
                    CheckboxOption(id = I5, label = INTEL_CORE_I5),
                    CheckboxOption(id = I7, label = INTEL_CORE_I7),
                    CheckboxOption(id = I9, label = INTEL_CORE_I9)
                )
            ),
        )

        3 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        4 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        5 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        6 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        7 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        8 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        9 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        10 -> listOf(
            FilterUi.PriceRange(null, null),
            FilterUi.CheckboxGroup(
                filterKey = CONDITION,
                titleRes = R.string.condition,
                options = listOf(
                    CheckboxOption(id = NEW, labelRes = R.string.condition_new),
                    CheckboxOption(id = PRE_OWNED, labelRes = R.string.condition_pre_owned),
                    CheckboxOption(id = OPEN_BOX, labelRes = R.string.condition_open_box)
                )
            ),
            FilterUi.CheckboxGroup(
                filterKey = BRAND,
                titleRes = R.string.brand,
                options = listOf(
                    CheckboxOption(id = ASUS, label = ASUS),
                    CheckboxOption(id = MSI, label = MSI),
                )
            ),
        )

        else -> emptyList()
    }
}