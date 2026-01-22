package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.orders.enums.OrdersTab
import com.example.academy_tbc.presentation.screen.home.orders.model.UiOrder

data class HomeState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val orders: List<UiOrder> = listOf(),
    val selectedTab: OrdersTab = OrdersTab.PENDING,
    val selectedOrder: UiOrder? = null
)