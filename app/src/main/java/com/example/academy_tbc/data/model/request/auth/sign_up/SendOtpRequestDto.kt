package com.example.academy_tbc.data.model.request.auth.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpRequestDto(
    val mobileNumber: String
)