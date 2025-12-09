package com.example.academy_tbc.presentation.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object DateFormatter {
    @OptIn(ExperimentalTime::class)
    fun Long.toPostDateString(timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
        val instant = Instant.fromEpochMilliseconds(this)

        val localDateTime = instant.toLocalDateTime(timeZone)

        val monthName = localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }

        val hour12 = if (localDateTime.hour % 12 == 0) 12 else localDateTime.hour % 12
        val amPm = if (localDateTime.hour >= 12) "PM" else "AM"

        val minuteStr = localDateTime.minute.toString().padStart(2, '0')

        return "${localDateTime.day} $monthName at $hour12:$minuteStr $amPm"
    }
}

