package com.example.academy_tbc.utils

import android.content.Context
import com.example.academy_tbc.R
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object Validations {
    @OptIn(ExperimentalTime::class)
    fun validateCardDetails(
        context: Context, cardHolder: String, cardNumber: String, expireDate: String, ccv: String
    ): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        val now = Clock.System.now()
        val currentDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val year = currentDateTime.year.toString()

        val lastTwoDigits = year.takeLast(2).toInt()



        if (cardHolder.isBlank()) {
            errors[KEY_CARD_HOLDER] = context.getString(R.string.cardholder_name_is_required)
        }

        if (cardNumber.isBlank()) {
            errors[KEY_CARD_NUMBER] = context.getString(R.string.card_number_is_required)
        } else if (cardNumber.length != 16) {
            errors[KEY_CARD_NUMBER] = context.getString(R.string.card_number_must_be_16_digits)
        }

        if (expireDate.isBlank() || expireDate.length != 5) {
            errors[KEY_EXPIRE_DATE] = context.getString(R.string.expiry_date_is_required)
        }

        if (expireDate.length == 5) {
            val parts = expireDate.split("/")
            val month = parts[0].toIntOrNull()
            val year = parts[1].toIntOrNull()

            if (month != null && month !in 1..12) {
                errors[KEY_EXPIRE_DATE] =
                    context.getString(R.string.expiry_date_must_be_in_mm_yy_format)
            }
            if (year != null && year < lastTwoDigits) {
                errors[KEY_EXPIRED_CARD] = context.getString(R.string.card_is_expired)
            }
        }

        if (ccv.isBlank()) {
            errors[KEY_CCV] = context.getString(R.string.ccv_is_required)
        } else if (ccv.length !in 3..4) {
            errors[KEY_CCV] = context.getString(R.string.ccv_must_be_3_or_4_digits)
        }

        return errors
    }

    const val KEY_CARD_HOLDER = "cardHolder"
    const val KEY_CARD_NUMBER = "cardNumber"
    const val KEY_EXPIRE_DATE = "expireDate"
    const val KEY_EXPIRED_CARD = "expiredCard"
    const val KEY_CCV = "ccv"
}
