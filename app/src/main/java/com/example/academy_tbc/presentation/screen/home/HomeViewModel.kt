package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.domain.usecase.order.GetOrdersByStatusUseCase
import com.example.academy_tbc.domain.usecase.order.UpdateOrderUseCase
import com.example.academy_tbc.presentation.common.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.orders.enums.OrdersTab
import com.example.academy_tbc.presentation.screen.home.orders.mapper.toPresentation
import com.example.academy_tbc.presentation.screen.home.orders.model.UiOrder
import com.example.academy_tbc.presentation.util.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersByStatusUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetOrders -> getOrders()
            is HomeEvent.TabSelected -> updateSelectedTab(event.tab)
            is HomeEvent.UpdateOrder -> updateOrder(event.id, event.status)
            is HomeEvent.SelectOrder -> selectOrder(event.id)
            HomeEvent.UnselectOrder -> unselectOrder()
        }
    }

    private fun selectOrder(uiOrder: UiOrder) {
        updateState { copy(selectedOrder = uiOrder) }
    }

    private fun unselectOrder() {
        updateState { copy(selectedOrder = null) }
    }

    private fun updateOrder(id: Int, status: OrderStatus) = viewModelScope.launch {
        updateState { copy(isLoading = true) }
        updateOrderUseCase(
            id = id,
            status = status
        )
            .onSuccess {
                updateState { copy(isLoading = false) }
                getOrders()
            }
            .onFailure {
                emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }

    private fun updateSelectedTab(selectedTab: OrdersTab) {
        updateState {
            copy(
                selectedTab = selectedTab,
                orders = listOf()
            )
        }
        getOrders()
    }

    private fun getOrders(isPullToRefresh: Boolean = false) = viewModelScope.launch {
        if (isPullToRefresh) {
            updateState { copy(isRefreshing = true) }
        } else {
            updateState { copy(isLoading = true) }
        }

        getOrdersUseCase(status = state.value.selectedTab.status)
            .onSuccess { outfitsDomain ->
                updateState {
                    copy(
                        orders = outfitsDomain.map { it.toPresentation() },
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
            .onFailure {
                emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId()))
                updateState { copy(isLoading = false, isRefreshing = false) }
            }
    }
}