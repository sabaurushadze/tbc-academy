package com.example.academy_tbc.screen.game

import com.example.academy_tbc.R

enum class Player(val symbol: Char, val drawableRes: Int) {
    X('X', R.drawable.x),
    O('O', R.drawable.o);

    fun other(): Player = if (this == X) O else X
}