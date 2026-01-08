package com.example.academy_tbc.presentation.screen.service

import android.annotation.SuppressLint
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class NotificationService : FirebaseMessagingService() {

    private companion object {
        const val TYPE = "type"
        const val TITLE = "title"
        const val BODY = "body"
    }

    @Inject
    lateinit var notificationHelper: NotificationHelper

    enum class Type { PROFILE, HOME }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val title = message.data[TITLE] ?: message.notification?.title ?: "New notification"
            val body = message.data[BODY] ?: message.notification?.body.orEmpty()

            when (Type.entries.find { it.name == message.data[TYPE] }) {

                Type.PROFILE -> {
                    notificationHelper.showProfileNotification(
                        title = title,
                        body = body,
                    )
                }

                Type.HOME -> {
                    notificationHelper.showHomeNotification(
                        title = title,
                        body = body,
                    )

                }

                null -> return
            }

        }

    }
}