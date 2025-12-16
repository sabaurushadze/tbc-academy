package com.example.academy_tbc.domain.model.pc_parts

import com.example.academy_tbc.domain.common.Constants.NEW
import com.example.academy_tbc.domain.common.Constants.OPEN_BOX
import com.example.academy_tbc.domain.common.Constants.PRE_OWNED

enum class Condition() {
    CONDITION_NEW,
    CONDITION_OPEN_BOX,
    CONDITION_PRE_OWNED;

    companion object {
        fun fromString(value: String): Condition =
            when (value.lowercase()) {
                NEW -> CONDITION_NEW
                OPEN_BOX -> CONDITION_OPEN_BOX
                PRE_OWNED -> CONDITION_PRE_OWNED
                else -> CONDITION_NEW
            }
    }
}
