package com.example.academy_tbc.data.remote.mapper.order

import com.example.academy_tbc.data.remote.dto.response.order.BorderDelayDetailsDto
import com.example.academy_tbc.data.remote.dto.response.order.BulkOrderDetailsDto
import com.example.academy_tbc.data.remote.dto.response.order.HighValueOrderDetailsDto
import com.example.academy_tbc.data.remote.dto.response.order.InTransitDetailsDto
import com.example.academy_tbc.data.remote.dto.response.order.OrderDetailsDto
import com.example.academy_tbc.data.remote.dto.response.order.OrderResponseDto
import com.example.academy_tbc.domain.model.order.BorderDelayDetails
import com.example.academy_tbc.domain.model.order.BulkOrderDetails
import com.example.academy_tbc.domain.model.order.HighValueOrderDetails
import com.example.academy_tbc.domain.model.order.InTransitDetails
import com.example.academy_tbc.domain.model.order.Order
import com.example.academy_tbc.domain.model.order.OrderDetails
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.presentation.util.DateConverter

fun OrderResponseDto.toDomain(): Order {
    return Order(
        id = id,
        status = OrderStatus.fromString(status),
        orderName = orderName,
        trackingNumber = trackingNumber,
        quantity = quantity.toString(),
        deliveryDate = DateConverter.millisecondsToDate(deliveryDate),
        subtotal = "$".plus(subtotal),
        details = details.toDomain()
    )
}

fun OrderDetailsDto.toDomain(): OrderDetails {
    return when (this) {
        is BorderDelayDetailsDto -> BorderDelayDetails(
            borderCountry = borderCountry,
            reason = reason,
            estimatedDelayDays = estimatedDelayDays
        )

        is InTransitDetailsDto -> InTransitDetails(
            currentCity = currentCity,
            nextCheckpoint = nextCheckpoint,
            progressPercent = progressPercent.toString().plus("%")
        )

        is HighValueOrderDetailsDto -> HighValueOrderDetails(
            insuredAmount = "$".plus(insuredAmount),
            requiresSignature = requiresSignature,
            fragile = fragile
        )

        is BulkOrderDetailsDto -> BulkOrderDetails(
            warehouseId = warehouseId,
            palletCount = palletCount,
            handlingInstructions = handlingInstructions
        )
    }
}