package com.example.academy_tbc.data.remote.dto.response.order

import com.example.academy_tbc.data.remote.dto.response.order.OrderDetailTypes.BORDER_DELAY
import com.example.academy_tbc.data.remote.dto.response.order.OrderDetailTypes.BULK
import com.example.academy_tbc.data.remote.dto.response.order.OrderDetailTypes.HIGH_VALUE
import com.example.academy_tbc.data.remote.dto.response.order.OrderDetailTypes.IN_TRANSIT
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseDto(
    val id: Int,
    val status: String,
    val orderName: String,
    val trackingNumber: String,
    val quantity: Int,
    val deliveryDate: Long,
    val subtotal: Int,
    val details: OrderDetailsDto
)

@Serializable
sealed interface OrderDetailsDto

@Serializable
@SerialName(BORDER_DELAY)
data class BorderDelayDetailsDto(
    val borderCountry: String,
    val reason: String,
    val estimatedDelayDays: Int
) : OrderDetailsDto

@Serializable
@SerialName(IN_TRANSIT)
data class InTransitDetailsDto(
    val currentCity: String,
    val nextCheckpoint: String,
    val progressPercent: Int
) : OrderDetailsDto

@Serializable
@SerialName(HIGH_VALUE)
data class HighValueOrderDetailsDto(
    val insuredAmount: Int,
    val requiresSignature: Boolean,
    val fragile: Boolean
) : OrderDetailsDto

@Serializable
@SerialName(BULK)
data class BulkOrderDetailsDto(
    val warehouseId: String,
    val palletCount: Int,
    val handlingInstructions: String
) : OrderDetailsDto

object OrderDetailTypes {
    const val BORDER_DELAY = "BORDER_DELAY"
    const val IN_TRANSIT = "IN_TRANSIT"
    const val HIGH_VALUE = "HIGH_VALUE"
    const val BULK = "BULK"
}