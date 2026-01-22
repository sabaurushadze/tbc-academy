package com.example.academy_tbc.domain.usecase.order

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.domain.repository.order.OrderRepository
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(id: Int, status: OrderStatus): Resource<Unit, DataError.Network> {
        return orderRepository.updateOrder(id, status)
    }
}