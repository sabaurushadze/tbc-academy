package com.example.academy_tbc.presentation.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)

object DateTimeParser {

    fun getMonthAbbr(isoString: String, timeZone: TimeZone = TimeZone.UTC): String {
        val instant = Instant.parse(isoString)
        val dateTime = instant.toLocalDateTime(timeZone)
        return dateTime.month.name.take(3)
    }

    fun getDay(isoString: String, timeZone: TimeZone = TimeZone.UTC): Int {
        val instant = Instant.parse(isoString)
        val dateTime = instant.toLocalDateTime(timeZone)
        return dateTime.day
    }

    fun getTime(isoString: String, timeZone: TimeZone = TimeZone.UTC): String {
        val instant = Instant.parse(isoString)
        val dateTime = instant.toLocalDateTime(timeZone)
        val hour12 = if (dateTime.hour % 12 == 0) 12 else dateTime.hour % 12
        val minute = dateTime.minute.toString().padStart(2, '0')
        val amPm = if (dateTime.hour < 12) AM else PM
        return "$hour12:$minute $amPm"
    }

    fun getFormattedDate(isoString: String, timeZone: TimeZone = TimeZone.UTC): String {
        val instant = Instant.parse(isoString)
        val dateTime = instant.toLocalDateTime(timeZone)

        val monthAbbr = dateTime.month.name.take(3).replaceFirstChar { it.uppercase() }
        val day = dateTime.day
        val year = dateTime.year

        return "$monthAbbr $day, $year"
    }

    private const val AM = "AM"
    private const val PM = "PM"
}