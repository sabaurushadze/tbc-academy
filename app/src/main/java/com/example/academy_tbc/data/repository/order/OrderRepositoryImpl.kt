package com.example.academy_tbc.data.repository.order

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.dto.request.order.UpdateOrderStatusRequestDto
import com.example.academy_tbc.data.remote.mapper.order.toDomain
import com.example.academy_tbc.data.remote.service.outfit.OrderService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.mapList
import com.example.academy_tbc.domain.model.order.Order
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.domain.repository.order.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val orderService: OrderService,
) : OrderRepository {
    override suspend fun getOrdersByStatus(status: OrderStatus): Resource<List<Order>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            orderService.getOrdersByStatus(status.name)
        }.mapList { it.toDomain() }
    }

    override suspend fun updateOrder(
        id: Int,
        status: OrderStatus,
    ): Resource<Unit, DataError.Network> {
        val dto = UpdateOrderStatusRequestDto(status.name)
        return apiResponseHandler.safeApiCall {
            orderService.updateOrder(id, dto)
        }
    }
}