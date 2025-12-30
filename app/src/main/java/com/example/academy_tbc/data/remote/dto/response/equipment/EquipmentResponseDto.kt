package com.example.academy_tbc.data.remote.dto.response.equipment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentResponseDto(
    val id: String,
    val name: String,
    @SerialName("name_de") val nameDe: String?,
    val createdAt: String,
    @SerialName("bgl_number") val bglNumber: String?,
    @SerialName("bgl_variant") val bglVariant: String?,
    @SerialName("order_id") val orderId: Int?,
    val main: String?,
    val children: List<EquipmentResponseDto> = emptyList(),
)