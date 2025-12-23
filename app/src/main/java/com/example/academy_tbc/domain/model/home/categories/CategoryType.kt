package com.example.academy_tbc.domain.model.home.categories

enum class CategoryType {
    TEAM_BUILDING,
    SPORTS,
    WORKSHOPS,
    HAPPY_FRIDAYS,
    CULTURAL,
    WELLNESS;

    companion object {
        fun fromInt(value: Int) =
            when (value) {
                1 -> TEAM_BUILDING
                2 -> SPORTS
                3 -> WORKSHOPS
                4 -> HAPPY_FRIDAYS
                5 -> CULTURAL
                6 -> WELLNESS
                else -> TEAM_BUILDING
            }

    }
}
