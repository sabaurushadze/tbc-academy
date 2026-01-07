package com.example.academy_tbc.presentation.screen.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import com.example.academy_tbc.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Singleton
class NotificationHelper @Inject constructor(@ApplicationContext context: Context) {

    private companion object {
        const val CONTENT_CHANNEL = "content_notifications"
        const val SOCIAL_CHANNEL = "social_notifications"
        const val BASE_PATH = "myapp://"
    }

    private val appContext = context.applicationContext

    private val notificationManager =
        context.getSystemService<NotificationManager>() ?: throw IllegalStateException()

    init {
        createChannel(CONTENT_CHANNEL, "Content", "Content updates")
        createChannel(SOCIAL_CHANNEL, "Social", "Reminders and alerts")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(id: String, name: String, desc: String) {
        if (notificationManager.getNotificationChannel(id) == null) {
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            channel.description = desc
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showProfileNotification(title: String, body: String) {
        val path = "${BASE_PATH}profile"
        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent = createPendingIntent(path.toUri(), requestCode)
        showNotification(title, body, pendingIntent, requestCode, CONTENT_CHANNEL)
    }

    fun showHomeNotification(title: String, body: String) {
        val path = "${BASE_PATH}home"
        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent = createPendingIntent(path.toUri(), requestCode)
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

    private fun createPendingIntent(uri: Uri, requestCode: Int): PendingIntent? {
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        return TaskStackBuilder.create(appContext)
            .run {
                addNextIntentWithParentStack(createDeepLinkIntent(uri))
                getPendingIntent(requestCode, flags)
            }
    }

    private fun createDeepLinkIntent(uri: Uri): Intent {
        return Intent(Intent.ACTION_VIEW, uri)
            .apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                addCategory(Intent.CATEGORY_BROWSABLE)

                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
    }
}
