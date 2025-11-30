package com.example.academy_tbc.domain.common

data class PreferenceKey<T>(
    val name: String,
    val defaultValue: T
)