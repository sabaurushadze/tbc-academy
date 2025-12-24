package com.example.academy_tbc.presentation.util

import kotlinx.datetime.LocalDateTime

object DateTimeParser {

    // Month abbreviation, e.g., "Jan"
    fun getMonthAbbr(isoString: String): String {
        val dateTime = LocalDateTime.parse(isoString)
        return dateTime.month.name.take(3).replaceFirstChar { it.uppercase() }
    }

    // Day of the month, e.g., 5
    fun getDay(isoString: String): Int {
        val dateTime = LocalDateTime.parse(isoString)
        return dateTime.day
    }

    // Time in 12-hour format, e.g., "5:00 PM"
    fun getTime(isoString: String): String {
        val dateTime = LocalDateTime.parse(isoString)
        val hour12 = if (dateTime.hour % 12 == 0) 12 else dateTime.hour % 12
        val minute = dateTime.minute.toString().padStart(2, '0')
        val amPm = if (dateTime.hour < 12) AM else PM
        return "$hour12:$minute $amPm"
    }

    // Formatted date, e.g., "Jan 5, 2026"
    fun getFormattedDate(isoString: String): String {
        val dateTime = LocalDateTime.parse(isoString)
        val monthAbbr = dateTime.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
        val day = dateTime.day
        val year = dateTime.year
        return "$monthAbbr $day, $year"
    }

    // Formatted date & time, e.g., "Jan 5, 2026 at 5:00 PM"
    fun getFormattedDateTime(isoString: String): String {
        val dateTime = LocalDateTime.parse(isoString)
        val monthAbbr = dateTime.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
        val day = dateTime.day
        val year = dateTime.year
        val hour12 = if (dateTime.hour % 12 == 0) 12 else dateTime.hour % 12
        val minute = dateTime.minute.toString().padStart(2, '0')
        val amPm = if (dateTime.hour < 12) AM else PM
        return "$monthAbbr $day, $year at $hour12:$minute $amPm"
    }

    private const val AM = "AM"
    private const val PM = "PM"
}