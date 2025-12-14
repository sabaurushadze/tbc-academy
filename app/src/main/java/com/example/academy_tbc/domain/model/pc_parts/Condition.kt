package com.example.academy_tbc.domain.model.pc_parts

enum class Condition(val apiValue: String) {
    NEW("new"),
    OPEN_BOX("open box"),
    PRE_OWNED("pre owned");

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
