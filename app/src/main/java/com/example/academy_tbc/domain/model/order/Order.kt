package com.example.academy_tbc.domain.model.order

data class Order(
    val id: Int,
    val status: OrderStatus,
    val orderName: String,
    val trackingNumber: String,
    val quantity: String,
    val deliveryDate: String,
    val subtotal: String,
    val details: OrderDetails,
)

enum class OrderStatus {
    PENDING,
    DELIVERED,
    CANCELED;

    companion object {
        fun fromString(value: String): OrderStatus {
            return when (value) {
                "PENDING" -> PENDING
                "DELIVERED" -> DELIVERED
                "CANCELED" -> CANCELED
                else -> PENDING
            }
        }
    }
}

sealed interface OrderDetails

data class BorderDelayDetails(
    val borderCountry: String,
    val reason: String,
    val estimatedDelayDays: Int,
) : OrderDetails

data class InTransitDetails(
    val currentCity: String,
    val nextCheckpoint: String,
    val progressPercent: String,
) : OrderDetails

data class HighValueOrderDetails(
    val insuredAmount: String,
    val requiresSignature: Boolean,
    val fragile: Boolean,
) : OrderDetails

data class BulkOrderDetails(
    val warehouseId: String,
    val palletCount: Int,
    val handlingInstructions: String,
) : OrderDetails