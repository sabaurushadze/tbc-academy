package com.example.challenge.presentation.screen.connection.mapper

import com.example.challenge.domain.model.connection.GetConnection
import com.example.challenge.presentation.screen.connection.model.UiConnection

fun GetConnection.toPresentation(): UiConnection {
    return UiConnection(
        avatar = avatar,
        email = email,
        id = id,
        fullName = fullName
    )
}