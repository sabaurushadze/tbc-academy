package com.example.academy_tbc.presentation.screen.home.orders.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.order.BorderDelayDetails
import com.example.academy_tbc.domain.model.order.BulkOrderDetails
import com.example.academy_tbc.domain.model.order.HighValueOrderDetails
import com.example.academy_tbc.domain.model.order.InTransitDetails
import com.example.academy_tbc.domain.model.order.Order
import com.example.academy_tbc.domain.model.order.OrderDetails
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.presentation.screen.home.orders.model.UiBorderDelayDetails
import com.example.academy_tbc.presentation.screen.home.orders.model.UiBulkOrderDetails
import com.example.academy_tbc.presentation.screen.home.orders.model.UiHighValueOrderDetails
import com.example.academy_tbc.presentation.screen.home.orders.model.UiInTransitDetails
import com.example.academy_tbc.presentation.screen.home.orders.model.UiOrder
import com.example.academy_tbc.presentation.screen.home.orders.model.UiOrderDetails

fun Order.toPresentation(): UiOrder {
    return UiOrder(
        id = id,
        statusTextRes = status.toStringRes(),
        status = status,
        orderName = orderName,
        trackingNumber = trackingNumber,
        quantity = quantity,
        deliveryDate = deliveryDate,
        subtotal = subtotal,
        details = details.toDomain()
    )
}

fun OrderDetails.toDomain(): UiOrderDetails {
    return when (this) {
        is BorderDelayDetails -> UiBorderDelayDetails(
            borderCountry = borderCountry,
            reason = reason,
            estimatedDelayDays = estimatedDelayDays
        )

        is InTransitDetails -> UiInTransitDetails(
            currentCity = currentCity,
            nextCheckpoint = nextCheckpoint,
            progressPercent = progressPercent
        )

        is HighValueOrderDetails -> UiHighValueOrderDetails(
            insuredAmount = insuredAmount,
            requiresSignature = requiresSignature,
            fragile = fragile
        )

        is BulkOrderDetails -> UiBulkOrderDetails(
            warehouseId = warehouseId,
            palletCount = palletCount,
            handlingInstructions = handlingInstructions
        )
    }
}

fun OrderStatus.toStringRes(): Int {
    return when (this) {
        OrderStatus.PENDING -> R.string.order_status_pending
        OrderStatus.DELIVERED -> R.string.order_status_delivered
        OrderStatus.CANCELED -> R.string.order_status_canceled
    }
}