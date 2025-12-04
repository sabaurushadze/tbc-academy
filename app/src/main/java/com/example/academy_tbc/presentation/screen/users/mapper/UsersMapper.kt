package com.example.academy_tbc.presentation.screen.users.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.status.ActivationStatus
import com.example.academy_tbc.domain.model.users.User
import com.example.academy_tbc.presentation.screen.users.model.UserUi

fun User.toPresentation(): UserUi {
    return UserUi(
        id = id,
        fullName = fullName,
        email = email,
        activationStatus = activationStatus.toUiText(),
        profileImageUrl = profileImageUrl
    )
}

fun ActivationStatus.toUiText() = when (this) {
    ActivationStatus.NOT_ACTIVATED -> R.string.not_activated
    ActivationStatus.ONLINE -> R.string.online
    ActivationStatus.FEW_MIN_AGO -> R.string.active_a_few_minutes_ago
    ActivationStatus.FEW_HOURS_AGO -> R.string.active_a_few_hours_ago
    ActivationStatus.INACTIVE_LONG -> R.string.inactive_for_a_long_time
}