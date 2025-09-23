package com.example.academy_tbc.task

import kotlin.math.abs

/**
 * შეიცავს მათემატიკურ ფუნქციებს
 */
internal class MathOperations {
    /**
     * აბრუნებს ორ მთელ რიცხვის უდიდეს საერთო გამყოფს
     *
     * @param a მთელი რიცხვი
     * @param b მთელი რიცხვი
     */
    internal fun usg(a: Int, b: Int): Int {
        if (a == 0 || b == 0) {
            throw ZeroException("a or b should not contain 0")
        }

        var numA = a
        var numB = b

        while (numB != 0) {
            val temp = numB
            numB = numA % numB
            numA = temp
        }

        return abs(numA)
    }

    /**
     * აბრუნებს ორ მთელ რიცხვის უმცირეს საერთო ჯერადს. ფუნქციის ფორმულისთვის გამოიყენება უდიდესი საერთო გამყოფის ფუნქცია [usg]
     *
     * @param a მთელი რიცხვი
     * @param b მთელი რიცხვი
     */
    internal fun usj(a: Int, b: Int): Int {
        return abs(a * b / usg(a, b))
    }

    /**
     * აბრუნებს `true`, თუ ტექსტი შეიცავს `$` სიმბოლოს
     *
     * @param text ტექსტი, რომელშიც გვსურს შევამოწმოთ არის თუ არა `$` სიმბოლო
     */
    internal fun containsDollarSymbol(text: String): Boolean {
        return text.contains("$")
    }

    /**
     * აბრუნებს ყველა ლუწი რიცხვების ჯამს 0-დან 100-მდე
     *
     * @param n რიცხვი, საიდანაც გვსურს დავიწყოთ ათვლა. საწყისი პარამეტრი `0`
     */
    internal fun recursiveSum(n: Int = 0): Int {
        if (n > 100) return 0
        if (n % 2 == 1) return recursiveSum(n + 1)
        return n + recursiveSum(n + 2)
    }

    /**
     * აბრუნებს მთელი რიცხვის შებრუნებულს
     *
     *  @param n მთელი რიცხვი, რომელიც უნდა შემობრუნდეს
     */
    internal fun reverseNumber(n: Int): Int {
        return try {
            when {
                n < 0 -> -n.toString().removePrefix("-").reversed().toInt()
                else -> n.toString().reversed().toInt()
            }
        } catch (e: NumberFormatException) {
            0
        }
    }

    /**
     * აბრუნებს `true` თუ ტექსტი არის პალინდრომი
     *
     * ლათინურ ტექსტში დიდ ასოებს აპატარავებს და შლის ცარიელ ადგილებს
     * და სიმბოლოებს, რომელიც არ არის ლათინური ასო ან ციფრი.
     *
     *  @param text ტექსტი, რომლის პალინდრომობა უნდა შემოწმდეს
     *  @return `true` თუ ტექსტი არის პალინდრომი, წინააღმდეგ შემთხვევაში `false`
     */
    internal fun isPalindrome(text: String): Boolean {
        val lowercaseText = text.lowercase()
        val removedTextWhitespace = lowercaseText.filter { !it.isWhitespace() }
        val cleanedText = removedTextWhitespace.filter { it.isLetterOrDigit() }
        return cleanedText == cleanedText.reversed()
    }
}

fun main() {
    val mathOperations = MathOperations()

//  ტესტირება
    val usg = mathOperations.usg(15, 5)
    val usj = mathOperations.usj(625, 25)

    val containsDollarNegative = mathOperations.containsDollarSymbol("TBC Academy Android Course")
    val containsDollarPositive = mathOperations.containsDollarSymbol("TBC Currency rates - 100$: 266.3₾")

    val sumEvenNumbers1 = mathOperations.recursiveSum(0)
    val sumEvenNumbers2 = mathOperations.recursiveSum()

    val reverseNumber1 = mathOperations.reverseNumber(10220)
    val reverseNumber2 = mathOperations.reverseNumber(-2003)
    val reverseNumber3 = mathOperations.reverseNumber(0)
//  Overflow -> 0
    val reverseNumber4 = mathOperations.reverseNumber(1234567899)

    val isPalindrome1 = mathOperations.isPalindrome(" ")
    val isPalindrome2 = mathOperations.isPalindrome("a")
    val isPalindrome3 = mathOperations.isPalindrome("Kotlin")
    val isPalindrome4 = mathOperations.isPalindrome("A man, a plan, a canal: Panama")

    println("usg: $usg")
    println("usj: $usj")

    println("Text contains $: $containsDollarNegative")
    println("Text contains $: $containsDollarPositive")

    println(sumEvenNumbers1)
    println(sumEvenNumbers2)

    println("Reversed Number: $reverseNumber1")
    println("Reversed Number: $reverseNumber2")
    println("Reversed Number: $reverseNumber3")
    println("Reversed Number: $reverseNumber4")

    println("Palindrome: $isPalindrome1")
    println("Palindrome: $isPalindrome2")
    println("Palindrome: $isPalindrome3")
    println("Palindrome: $isPalindrome4")
}