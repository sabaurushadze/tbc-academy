package com.example.academy_tbc.screen.game_config

enum class BoardSize(val size: Int, val label: String) {
    THREE(3, "3x3"),
    FOUR(4, "4x4"),
    FIVE(5, "5x5");

    companion object {
        fun fromLabel(label: String) = entries.find { it.label == label }
    }
}