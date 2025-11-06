package com.example.academy_tbc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.academy_tbc.screen.card_management.CardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import java.util.UUID

class CardViewModel : ViewModel() {

    private val cardsJson = """
        [
            {
                "id": "${UUID.randomUUID()}",
                "cardType": "mastercard",
                "cardNumber": "4364134589328378",
                "cardHolder": "Saba Urushadze",
                "validThru": "12/25"
            },
            {
                "id": "${UUID.randomUUID()}",
                "cardType": "visa",
                "cardNumber": "1234133382128312",
                "cardHolder": "Gela Mazmishvili",
                "validThru": "05/29"
            },
            {
                "id": "${UUID.randomUUID()}",
                "cardType": "visa",
                "cardNumber": "9876543210123456",
                "cardHolder": "Saxeli Gvarishvili",
                "validThru": "01/26"
            }
        ]
    """.trimIndent()
    private val _uiState = MutableStateFlow(
        CardUiState(
            cardList = Json.decodeFromString<List<CardItem>>(cardsJson)
        )
    )
    val uiState: StateFlow<CardUiState> = _uiState.asStateFlow()

    fun changeCardType(cardType: CardItem.CardType) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCardType = cardType
            )
        }
    }

    fun deleteCard(cardId: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                cardList = currentState.cardList.filterNot { it.id == cardId })
        }
    }

    fun addNewCard(
        cardNumber: String, cardHolder: String, validThru: String, cardType: CardItem.CardType
    ) {
        val newCard = CardItem(
            cardNumber = cardNumber,
            cardHolder = cardHolder,
            validThru = validThru,
            cardType = cardType
        )
        _uiState.update { it.copy(cardList = it.cardList + newCard) }
    }

}

data class CardUiState(
    val cardList: List<CardItem> = emptyList(),
    val selectedCardType: CardItem.CardType = CardItem.CardType.MASTERCARD,
    val cardHolderName: String = "",
    val cardNumber: String = "",
    val validThru: String = "",
    val cvv: String = ""
)