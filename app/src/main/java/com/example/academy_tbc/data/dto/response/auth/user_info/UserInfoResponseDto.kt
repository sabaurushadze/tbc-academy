package com.example.academy_tbc.data.dto.response.auth.user_info

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseDto(
    val email: String,
    val name: String,
    val lastName: String,
    val roleId: Int,
    val isActive: Boolean
)