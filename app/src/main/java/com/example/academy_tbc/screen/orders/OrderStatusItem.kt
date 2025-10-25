package com.example.academy_tbc.screen.orders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class OrderStatusItem(
    val id: UUID,
    val orderStatus: OrderStatus,
    val isActivated: Boolean = false
) : Parcelable