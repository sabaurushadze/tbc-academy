package com.example.academy_tbc.domain.model.outfit

data class Outfit(
    val id: Int,
    val category: OutfitCategory,
    val name: String,
    val image: String,
    val price: Price
) {
    data class Price(
        val amount: Double,
        val currency: Currency
    )
}

enum class Currency {
    GEL,
    USD;

    companion object {
        fun fromString(value: String): Currency {
            return when(value) {
                "GEL" -> GEL
                "USD" -> USD
                else -> USD
            }
        }
    }
}

enum class OutfitCategory {
    PARTY,
    CAMPING,
    WEDDING,
    SPORTS,
    HIKING,
    OFFICE;

    companion object {
        fun fromInt(value: Int): OutfitCategory {
            return when(value) {
                1 -> PARTY
                2 -> CAMPING
                3 -> WEDDING
                4 -> SPORTS
                5 -> HIKING
                6 -> OFFICE
                else -> SPORTS
            }
        }
    }
}