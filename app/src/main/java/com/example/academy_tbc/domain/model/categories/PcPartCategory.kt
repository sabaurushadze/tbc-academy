package com.example.academy_tbc.domain.model.categories

enum class PcPartCategory {
    GPU,
    CPU,
    MOTHERBOARD,
    RAM,
    SSD,
    HDD,
    CPU_COOLER,
    PSU,
    CASE_COOLER,
    CASE;

    companion object {
        fun fromInt(value: Int): PcPartCategory =
            when (value) {
                1 -> GPU
                2 -> CPU
                3 -> MOTHERBOARD
                4 -> RAM
                5 -> SSD
                6 -> PSU
                7 -> CPU_COOLER
                8 -> HDD
                9 -> CASE_COOLER
                else -> CASE
            }
    }
}