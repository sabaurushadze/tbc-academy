package com.example.academy_tbc.common

import kotlinx.serialization.Serializable
@Serializable
data class ErrorMessage(
    val error: String
)