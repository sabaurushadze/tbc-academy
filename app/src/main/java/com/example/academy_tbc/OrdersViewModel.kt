package com.example.academy_tbc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class OrdersViewModel : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderItem>>(emptyList())
    val orders: StateFlow<List<OrderItem>> = _orders.asStateFlow()

    private val _selectedOrder = MutableStateFlow<OrderItem?>(null)
    val selectedOrder: StateFlow<OrderItem?> = _selectedOrder.asStateFlow()


    init {
        _orders.value = sampleOrders()
    }

    fun selectOrder(item: OrderItem) {
        _selectedOrder.value = item
    }

    fun markOrderReviewed() {
        _selectedOrder.value?.let { order ->
            val updatedOrder = order.copy(isReviewed = true)
            _selectedOrder.value = updatedOrder

            val updatedList = _orders.value.map { if (it.id == order.id) updatedOrder else it }
            _orders.value = updatedList
        }
    }

    private fun sampleOrders() = listOf(
        OrderItem(UUID.randomUUID(), R.drawable.img_product1, "Ofisis skami", R.color.white, "White", 5, 238, OrderStatus.ACTIVE),
        OrderItem(UUID.randomUUID(), R.drawable.img_product2, "Jorko", R.color.black, "Black", 100, 123, OrderStatus.COMPLETED),
        OrderItem(UUID.randomUUID(), R.drawable.img_product3, "Magari shuqi", R.color.black, "Blue", 3, 444, OrderStatus.COMPLETED),
        OrderItem(UUID.randomUUID(), R.drawable.img_product4, "Savardzeli", R.color.black, "Green", 1, 38, OrderStatus.ACTIVE)
    )
}