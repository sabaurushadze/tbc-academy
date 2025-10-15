package com.example.academy_tbc.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Convertors {
    fun convertLongToDate(time: String): String {
        val date = Date(time.toLong())
        val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return format.format(date)
    }
}