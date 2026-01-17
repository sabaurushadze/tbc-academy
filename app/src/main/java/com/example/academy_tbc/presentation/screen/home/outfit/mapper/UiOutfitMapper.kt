package com.example.academy_tbc.presentation.screen.home.outfit.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.outfit.Currency
import com.example.academy_tbc.domain.model.outfit.Outfit
import com.example.academy_tbc.presentation.screen.home.outfit.model.UiOutfit

fun Outfit.toPresentation(): UiOutfit {
    return UiOutfit(
        id = id,
        category = category,
        name = name,
        image = image,
        priceAmount = price.amount,
        currencyRes = price.currency.toDisplayString(),
    )
}

fun Currency.toDisplayString(): Int =
    when (this) {
        Currency.GEL -> R.string.currency_gel
        Currency.USD -> R.string.currency_usd
    }