package com.example.academy_tbc.data.remote.dto.request.order

import kotlinx.serialization.Serializable

@Serializable
data class UpdateOrderStatusRequestDto(
    val status: String
)