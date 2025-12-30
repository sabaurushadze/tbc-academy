package com.example.academy_tbc.domain.model.equipment

data class Equipment(
    val id: String,
    val name: String,
    val nameDe: String?,
    val createdAt: String,
    val bglNumber: String?,
    val bglVariant: String?,
    val orderId: Int?,
    val main: String?,
    val children: List<Equipment> = emptyList(),
)