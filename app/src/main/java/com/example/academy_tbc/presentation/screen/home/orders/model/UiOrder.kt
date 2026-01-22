package com.example.academy_tbc.presentation.screen.home.orders.model

import androidx.annotation.StringRes
import com.example.academy_tbc.domain.model.order.OrderStatus

data class UiOrder(
    val id: Int,
    @param:StringRes val statusTextRes: Int,
    val status: OrderStatus,
    val orderName: String,
    val trackingNumber: String,
    val quantity: String,
    val deliveryDate: String,
    val subtotal: String,
    val details: UiOrderDetails,
)

sealed interface UiOrderDetails

data class UiBorderDelayDetails(
    val borderCountry: String,
    val reason: String,
    val estimatedDelayDays: Int,
) : UiOrderDetails

data class UiInTransitDetails(
    val currentCity: String,
    val nextCheckpoint: String,
    val progressPercent: String,
) : UiOrderDetails

data class UiHighValueOrderDetails(
    val insuredAmount: String,
    val requiresSignature: Boolean,
    val fragile: Boolean,
) : UiOrderDetails

data class UiBulkOrderDetails(
    val warehouseId: String,
    val palletCount: Int,
    val handlingInstructions: String,
) : UiOrderDetails