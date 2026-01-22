package com.example.academy_tbc.domain.repository.order

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.order.Order
import com.example.academy_tbc.domain.model.order.OrderStatus

interface OrderRepository {
    suspend fun getOrdersByStatus(status: OrderStatus): Resource<List<Order>, DataError.Network>
    suspend fun updateOrder(id: Int, status: OrderStatus): Resource<Unit, DataError.Network>
}