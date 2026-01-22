package com.example.academy_tbc.data.remote.service.outfit

import com.example.academy_tbc.data.remote.dto.request.order.UpdateOrderStatusRequestDto
import com.example.academy_tbc.data.remote.dto.response.order.OrderResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderService {
    @GET(ORDER)
    suspend fun getOrdersByStatus(
        @Query("status") status: String,
    ): Response<List<OrderResponseDto>>

    @PATCH("orders/{id}")
    suspend fun updateOrder(
        @Path("id") id: Int,
        @Body order: UpdateOrderStatusRequestDto,
    ): Response<Unit>

    companion object {
        private const val ORDER = "orders"
    }
}