package com.example.academy_tbc

import android.content.Context


class NumberToTextConverterEnglish(
    userInput: String, context: Context
) : NumberToTextConverter(userInput, context) {

    override val singleDigits = mapOf(
        "1" to "one",
        "2" to "two",
        "3" to "three",
        "4" to "four",
        "5" to "five",
        "6" to "six",
        "7" to "seven",
        "8" to "eight",
        "9" to "nine",
    )
    override val elevenToNineteen = mapOf(
        "11" to "eleven",
        "12" to "twelve",
        "13" to "thirteen",
        "14" to "fourteen",
        "15" to "fifteen",
        "16" to "sixteen",
        "17" to "seventeen",
        "18" to "eighteen",
        "19" to "nineteen",
    )
    override val tens = mapOf(
        "10" to "ten",
        "20" to "twenty",
        "30" to "thirty",
        "40" to "forty",
        "50" to "fifty",
        "60" to "sixty",
        "70" to "seventy",
        "80" to "eighty",
        "90" to "ninety",
    )
    override val hundreds = mapOf(
        "100" to "one hundred",
        "200" to "two hundred",
        "300" to "three hundred",
        "400" to "four hundred",
        "500" to "five hundred",
        "600" to "six hundred",
        "700" to "seven hundred",
        "800" to "eight hundred",
        "900" to "nine hundred",
    )


    override fun findTwoDigitText(input: String): String {
        val firstDigit = input[0]
        val secondDigit = input[1]

        var firstWord = ""
        var secondWord = ""

        for (pair in tens.entries) if (firstDigit == pair.key.first()) {
            firstWord = pair.value
            break
        }

        for (pair in singleDigits.entries) {
            if (secondDigit == pair.key.first()) {
                secondWord = pair.value
                break
            }
        }
        return when {
            firstDigit == '0' && secondDigit != '0' -> secondWord
            firstDigit != '0' && secondDigit == '0' -> firstWord
            firstDigit == '0' && secondDigit == '0' -> ""
            else -> "$firstWord-$secondWord"
        }
    }

    override fun findThreeDigitText(input: String): String {
        val firstDigitHundred = input.first()

        var valueAseuli = ""
        for (pair in hundreds.entries) {
            if (firstDigitHundred == pair.key.first()) {
                valueAseuli = pair.value
                break
            }
        }

        val secondAndThirdDigits = if (input[1] == '0') input.drop(2) else input.drop(1)

        val isPresentInMemory = isInputInMemory(secondAndThirdDigits)
        return if (isPresentInMemory.isNotEmpty()) {
            "$valueAseuli $isPresentInMemory"
        } else {
            val secondAndThirdDigitsText = findTwoDigitText(secondAndThirdDigits)
            return "$valueAseuli $secondAndThirdDigitsText"
        }
    }
}