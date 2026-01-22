package com.example.academy_tbc.presentation.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object DateConverter {

    @OptIn(ExperimentalTime::class)
    fun millisecondsToDate(milliseconds: Long): String {
        val instant = Instant.fromEpochMilliseconds(milliseconds)

        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        return "%02d/%02d/%04d".format(
            localDateTime.day,
            localDateTime.month.number,
            localDateTime.year
        )
    }
}