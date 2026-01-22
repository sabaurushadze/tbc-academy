package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.presentation.screen.home.orders.enums.OrdersTab
import com.example.academy_tbc.presentation.screen.home.orders.model.UiOrder

sealed class HomeEvent {
    data object GetOrders : HomeEvent()
    data class TabSelected(val tab: OrdersTab) : HomeEvent()
    data class UpdateOrder(val id: Int, val status: OrderStatus) : HomeEvent()
    data class SelectOrder(val id: UiOrder) : HomeEvent()
    data object UnselectOrder : HomeEvent()
}