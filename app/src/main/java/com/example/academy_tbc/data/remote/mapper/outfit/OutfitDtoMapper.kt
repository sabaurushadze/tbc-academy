package com.example.academy_tbc.data.remote.mapper.outfit

import com.example.academy_tbc.data.remote.dto.response.outfit.OutfitResponseDto
import com.example.academy_tbc.domain.model.outfit.Currency
import com.example.academy_tbc.domain.model.outfit.Outfit
import com.example.academy_tbc.domain.model.outfit.OutfitCategory

fun OutfitResponseDto.toDomain(): Outfit {
    return Outfit(
        id = id,
        category = OutfitCategory.fromInt(category),
        name = name,
        image = image,
        price = Outfit.Price(
            amount = price.amount,
            currency = Currency.fromString(price.currency)
        )
    )
}