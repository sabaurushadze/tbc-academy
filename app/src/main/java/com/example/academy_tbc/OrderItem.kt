package com.example.academy_tbc

import androidx.annotation.DrawableRes
import java.util.UUID

data class OrderItem(
    val id: UUID,
    @param:DrawableRes val image: Int,
    val title: String,
    val productColor: Int,
    val productColorName: String,
    val quantity: Int,
    val price: Int,
    val status: OrderStatus,
    val isReviewed: Boolean = false
)