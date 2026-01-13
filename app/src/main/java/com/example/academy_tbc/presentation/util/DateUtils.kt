package com.example.academy_tbc.presentation.util

import kotlinx.datetime.*
import kotlinx.datetime.format.char
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
object DateUtils {
    fun getCurrentEpoch(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }
    fun getTimeStamp(): String {
        val customFormat = LocalDateTime.Format {
            year()
            monthNumber()
            day()
            char('_')
            hour()
            minute()
            second()
        }

        val currentMoment: Instant = Clock.System.now()
        val systemTz = TimeZone.currentSystemDefault()
        val localDateTime = currentMoment.toLocalDateTime(systemTz)

        val timeStamp = localDateTime.format(customFormat)
        return timeStamp
    }


}
