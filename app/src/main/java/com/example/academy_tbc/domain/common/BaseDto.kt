package com.example.academy_tbc.domain.common

import kotlinx.serialization.Serializable

@Serializable
data class  BaseDto<T>(
    val data: T? = null,
    val errorCode: String? = null,
    val errorMessage: String? = null,
)
