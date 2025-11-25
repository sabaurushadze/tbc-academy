package com.example.academy_tbc.common

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String
)