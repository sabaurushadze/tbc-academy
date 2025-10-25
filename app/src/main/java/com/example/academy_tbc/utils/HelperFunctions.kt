package com.example.academy_tbc.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HelperFunctions {
    fun convertMillisecondsToDate(milliseconds: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val dateString = formatter.format(Date(milliseconds))
        return dateString
    }
}