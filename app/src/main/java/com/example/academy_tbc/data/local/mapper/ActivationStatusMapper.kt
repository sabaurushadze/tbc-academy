package com.example.academy_tbc.data.local.mapper

import com.example.academy_tbc.domain.model.status.ActivationStatus

fun Int.toActivationStatus(): ActivationStatus =
    when {
        this <= 0 -> ActivationStatus.NOT_ACTIVATED
        this == 1 -> ActivationStatus.ONLINE
        this == 2 -> ActivationStatus.FEW_MIN_AGO
        this in 3..22 -> ActivationStatus.FEW_HOURS_AGO
        else -> ActivationStatus.INACTIVE_LONG
    }