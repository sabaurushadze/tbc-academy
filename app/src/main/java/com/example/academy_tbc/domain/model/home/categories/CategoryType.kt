package com.example.academy_tbc.domain.model.home.categories

enum class CategoryType(val id: Int) {
    TEAM_BUILDING(1),
    SPORTS(2),
    WORKSHOPS(3),
    HAPPY_FRIDAYS(4),
    CULTURAL(5),
    WELLNESS(6);

    companion object {
        fun fromId(id: Int): CategoryType? = entries.firstOrNull { it.id == id }
    }
}
