package com.example.academy_tbc.task2

import kotlin.random.Random

fun main() {
    var playAgain = true

    while (playAgain) {

        val x = readNonEmptyInput("Enter the value of the X variable: ")
        val y = readNonEmptyInput("Enter the value of the Y variable: ")
        println()

        val operation = readOperationInput()

        val xInt: Int = extractDigitsOrRandom(x)
        val yInt: Int = extractDigitsOrRandom(y)

        val xDouble = xInt.toDouble()
        val yDouble = yInt.toDouble()

        when (operation) {
            "/" -> {
                if (yInt == 0) {
                    println("Can't divide by zero")
                } else {
                    val division = xDouble / yDouble
                    println("X and Y $operation is: $division")
                }
            }
            "*" -> {
                val multiplication = xInt * yInt
                println("X and Y $operation is: $multiplication")
            }
            "%" -> {
                if (yInt == 0) {
                    println("Can't divide by zero")
                } else {
                    val modulus = xInt % yInt
                    println("X and Y $operation is: $modulus")
                }
            }
            "!" -> {
                if (yInt == 0) {
                    println("Can't divide by zero")
                } else {
                    val division = xInt / yInt
                    when {
                        division >= 21 -> println("Woah, that's a big number, I can't calculate that :)")
                        division < 0 -> println("Can't calculate the factorial with a negative number")
                        else -> println("X and Y $operation is: ${factorial(division)}")
                    }
                }
            }
        }

        val tryAgain = readNonEmptyInput("Try again <Y/N>?")
        playAgain = tryAgain.equals("Y", ignoreCase = true)
    }
}

fun readOperationInput(): String {
    while (true) {
        print(
            "Division: /\n" +
                    "Multiplication: *\n" +
                    "The remainder obtained when dividing: %\n" +
                    "Factorial of the division: !\n\n" +
                    "Enter the operation type: "
        )
        val input = readln().trim()
        if ((input in listOf("*","/","%","!")) && input.length == 1) return input
        println("Please enter a valid operation (*, /, %, !)")
    }
}

fun readNonEmptyInput(prompt: String): String {
    while (true) {
        print(prompt)
        val input = readln().trim()
        if (input.isNotEmpty()) return input
        println("The field must not be empty!")
    }
}

fun extractDigitsOrRandom(input: String): Int {
    var isNegative = false
    if (input.first() == '-') isNegative = true

    val digits = input.filter { it.isDigit() }

    return if (digits.isNotEmpty() && !isNegative) {
        digits.toInt()
    } else if (digits.isNotEmpty() && isNegative) {
        -digits.toInt()
    } else {
        Random.nextInt(-127, 130)
    }
}

fun factorial(n: Int): Long {
    var result = 1L
    for (i in 2..n) {
        result *= i
    }
    return result
}