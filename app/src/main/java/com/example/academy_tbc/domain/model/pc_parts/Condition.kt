package com.example.academy_tbc.domain.model.pc_parts

enum class Condition {
    NEW,
    OPEN_BOX,
    PRE_OWNED;

    companion object {
        fun fromString(value: String): Condition =
            when (value.lowercase()) {
                "new" -> NEW
                "open box" -> OPEN_BOX
                "pre owned" -> PRE_OWNED
                else -> NEW
            }
    }
}