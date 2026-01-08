package com.example.academy_tbc.presentation.screen.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.navigation.NavDeepLinkBuilder
import com.example.academy_tbc.MainActivity
import com.example.academy_tbc.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(@ApplicationContext context: Context) {

    private companion object {
        const val CONTENT_CHANNEL = "content_notifications"
        const val SOCIAL_CHANNEL = "social_notifications"
    }

    private val appContext = context.applicationContext

    private val notificationManager =
        context.getSystemService<NotificationManager>()!!

    init {
        createChannel(CONTENT_CHANNEL, "Content", "Content updates")
        createChannel(SOCIAL_CHANNEL, "Social", "Reminders and alerts")
    }


    private fun createChannel(id: String, name: String, desc: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            channel.description = desc
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showProfileNotification(title: String, body: String) {
        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent = getPendingIntentForDestination(R.id.profileFragment)
        showNotification(title, body, pendingIntent, requestCode, CONTENT_CHANNEL)
    }

    fun showHomeNotification(title: String, body: String) {
        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent = getPendingIntentForDestination(R.id.homeFragment)
        showNotification(title, body, pendingIntent, requestCode, SOCIAL_CHANNEL)

    }

    private fun showNotification(
        title: String,
        body: String,
        pendingIntent: PendingIntent?,
        requestCode: Int,
        channelId: String,
    ) {

        val notification = NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(R.drawable.circle_gradient)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(requestCode, notification)
    }

    private fun getPendingIntentForDestination(destinationId: Int): PendingIntent {
        return NavDeepLinkBuilder(appContext)
            .setGraph(R.navigation.nav_graph)
            .setComponentName(MainActivity::class.java)
            .setDestination(destinationId)
            .createPendingIntent()
    }
}
