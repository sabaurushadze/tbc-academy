package com.example.academy_tbc.domain.usecase.order

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.order.Order
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.domain.repository.order.OrderRepository
import javax.inject.Inject

class GetOrdersByStatusUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(status: OrderStatus): Resource<List<Order>, DataError.Network> {
        return orderRepository.getOrdersByStatus(status)
    }
}