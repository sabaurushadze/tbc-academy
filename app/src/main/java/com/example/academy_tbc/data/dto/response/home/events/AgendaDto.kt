package com.example.academy_tbc.data.dto.response.home.events

import kotlinx.serialization.Serializable

@Serializable
data class AgendaDto(
    val id: Int,
    val time: String,
    val title: String,
    val description: String? = null
)