package com.example.academy_tbc.presentation.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object DateFormatter {

    @OptIn(ExperimentalTime::class)
    fun formatDateTime(epochMillis: Long): String {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        val day = localDateTime.day
        val month = localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
        val hour =
            if (localDateTime.hour == 0 || localDateTime.hour == 12) 12 else localDateTime.hour % 12
        val minute = localDateTime.minute.toString().padStart(2, '0')
        val amPm = if (localDateTime.hour < 12) "AM" else "PM"

        return "$day $month at $hour:$minute $amPm"
    }

}