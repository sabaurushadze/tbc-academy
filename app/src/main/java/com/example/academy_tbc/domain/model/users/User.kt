package com.example.academy_tbc.domain.model.users

import com.example.academy_tbc.domain.model.status.ActivationStatus

data class User(
    val id: Int,
    val fullName: String,
    val email: String,
    val activationStatus: ActivationStatus,
    val profileImageUrl: String?,
)