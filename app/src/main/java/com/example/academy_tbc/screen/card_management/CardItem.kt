package com.example.academy_tbc.screen.card_management

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CardItem(
    val id: String = UUID.randomUUID().toString(),
    val cardType: CardType = CardType.VISA,
    val cardNumber: String,
    val cardHolder: String,
    val validThru: String,
) {
    @Serializable
    enum class CardType {
        @SerialName("visa")
        VISA,

        @SerialName("mastercard")
        MASTERCARD
    }
}
