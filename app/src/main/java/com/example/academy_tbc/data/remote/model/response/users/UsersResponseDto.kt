package com.example.academy_tbc.data.remote.model.response.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponseDto(
    val id: Int,
    @SerialName("full_name") val fullName: String,
    val email: String,
    @SerialName("activation_status") val activationStatus: Int,
    @SerialName("profile_image_url") val profileImageUrl: String?,
)