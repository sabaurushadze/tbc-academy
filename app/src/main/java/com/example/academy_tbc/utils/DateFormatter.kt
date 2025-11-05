package com.example.academy_tbc.utils

import android.content.Context
import com.example.academy_tbc.R
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

//ar chavawodot context garedan, vm, class.
// objectshi unda iyos
@OptIn(ExperimentalTime::class)
fun formatMessageTime(context: Context, epochMillis: Long): String {
    val zone = TimeZone.currentSystemDefault()
    val now = Clock.System.now().toLocalDateTime(zone)
    val msgTime = Instant.fromEpochMilliseconds(epochMillis).toLocalDateTime(zone)
    // unda iyos try catch negative long
    val dateLabel = when (msgTime.date) {
        now.date -> context.getString(R.string.today)
        now.date.minus(1, DateTimeUnit.DAY) -> context.getString(R.string.yesterday)
        else -> msgTime.date.toString()
    }

    val hour = msgTime.hour % 12
    val displayHour = if (hour == 0) 12 else hour
    val minute = msgTime.minute.toString().padStart(2, '0')
    val amPm =
        if (msgTime.hour < 12) context.getString(R.string.am) else context.getString(R.string.pm)

    return "$dateLabel, $displayHour:$minute $amPm"
}