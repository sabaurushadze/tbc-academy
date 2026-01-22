package com.example.academy_tbc.presentation.screen.home.orders.enums

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.order.OrderStatus

enum class OrdersTab(
    val status: OrderStatus,
    val titleRes: Int
) {
    PENDING(OrderStatus.PENDING, R.string.order_status_pending),
    DELIVERED(OrderStatus.DELIVERED, R.string.order_status_delivered),
    CANCELED(OrderStatus.CANCELED, R.string.order_status_canceled)
}