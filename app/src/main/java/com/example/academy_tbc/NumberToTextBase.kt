package com.example.academy_tbc

import android.content.Context

open class NumberToTextConverter(protected var userInput: String, val context: Context) {
    protected open val singleDigits = mapOf(
        "1" to "ერთი",
        "2" to "ორი",
        "3" to "სამი",
        "4" to "ოთხი",
        "5" to "ხუთი",
        "6" to "ექვსი",
        "7" to "შვიდი",
        "8" to "რვა",
        "9" to "ცხრა",
    )
    protected open val elevenToNineteen = mapOf(
        "11" to "თერთმეტი",
        "12" to "თორმეტი",
        "13" to "ცამეტი",
        "14" to "თოთხმეტი",
        "15" to "თხუთმეტი",
        "16" to "თექვსმეტი",
        "17" to "ჩვიდმეტი",
        "18" to "თვრამეტი",
        "19" to "ცხრამეტი",
    )
    protected open val tens = mapOf(
        "10" to "ათი",
        "20" to "ოცი",
        "30" to "ოცდაათი",
        "40" to "ორმოცი",
        "50" to "ორმოცდაათი",
        "60" to "სამოცი",
        "70" to "სამოცდაათი",
        "80" to "ოთხმოცი",
        "90" to "ოთხმოცდაათი",
    )
    protected open val hundreds = mapOf(
        "100" to "ასი",
        "200" to "ორასი",
        "300" to "სამასი",
        "400" to "ოთხასი",
        "500" to "ხუთასი",
        "600" to "ექვსასი",
        "700" to "შვიდასი",
        "800" to "რვაასი",
        "900" to "ცხრაასი",
    )

    open fun isInputInMemory(input: String): String {
        var result = ""

        val digitsList: List<Map<String, String>> = when (input.length) {
            1 -> listOf(singleDigits)
            2 -> listOf(elevenToNineteen, tens)
            3 -> listOf(hundreds)
            else -> listOf(mapOf("" to ""))
        }

//          თუ `elevenToNineteen`-ში იპოვა ციფრი, `ateuli`-ში აღარ გააგრძელებს ძებნას და გამოვა ციკლიდან
        loop@ for (map in digitsList) {
            for (pair in map.entries) {
                if (input == pair.key) {
                    result = pair.value
                    break@loop
                }
            }
        }
        return result
    }


    protected open fun findTwoDigitText(input: String): String {
        val firstDigit = input[0]
        val secondDigit = input[1]

        var firstWord: String
        var secondWord = ""
        val isFirstDigitOdd = firstDigit.digitToInt() % 2 == 1
        var value = ""

        for (pair in tens.entries) if (firstDigit == pair.key.first()) {
            value = pair.value
            break
        }
        firstWord = when {
            isFirstDigitOdd -> value.dropLast(3)
            !isFirstDigitOdd -> value.dropLast(1) + "და"
            else -> value
        }
        val singleDigitsOrDouble = if (isFirstDigitOdd) elevenToNineteen else singleDigits
        for (pair in singleDigitsOrDouble.entries) {
            if (isFirstDigitOdd && secondDigit == pair.key[1]) {
                secondWord = pair.value
                break
            } else if (!isFirstDigitOdd && secondDigit == pair.key[0]) {
                secondWord = pair.value
                break
            }
        }
        return "$firstWord$secondWord"
    }

    protected open fun findThreeDigitText(input: String): String {
        val firstDigitHundred = input.first()

        var valueAseuli = ""
        for (pair in hundreds.entries) {
            if (firstDigitHundred == pair.key.first()) {
                valueAseuli = pair.value.dropLast(1)
                break
            }
        }

        val secondAndThirdDigits = if (input[1] == '0') input.drop(2) else input.drop(1)

        val isPresentInMemory = isInputInMemory(secondAndThirdDigits)
        return if (isPresentInMemory.isNotEmpty()) {
            "$valueAseuli$isPresentInMemory"
        } else {
            val secondAndThirdDigitsText = findTwoDigitText(secondAndThirdDigits)
            return "$valueAseuli$secondAndThirdDigitsText"
        }
    }

    internal open fun convertNumberToText(): String {
        if (userInput.startsWith('0')) {
            return "toastStartsZero"
        }
        val finalResult = isInputInMemory(userInput)

        return if (finalResult.isNotEmpty()) {
            finalResult
        } else if (userInput.length == 2) {
            findTwoDigitText(userInput)
        } else if (userInput.length == 3) {
            findThreeDigitText(userInput)
        } else if (userInput == "1000") {
            context.getString(R.string.one_thousand)
        } else {
            "toastNumberRange"
        }
    }


}
