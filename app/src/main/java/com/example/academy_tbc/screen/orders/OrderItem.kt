package com.example.academy_tbc.screen.orders

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class OrderItem (
    val id: UUID,
    val orderDate: Long,
    val orderName: String,
    val trackingNum: String,
    val quantity: Int,
    val subTotal: Int,
    var status: OrderStatus
) : Parcelable