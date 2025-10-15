package com.example.academy_tbc.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertTimestampToDate(timestamp: String): String {
    val timestampLong = timestamp.toLong()
    val date = Date(timestampLong)
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(date)
}