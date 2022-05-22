package com.carbonanik.bongotalkies.presentation.component

import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String {
    val stringToDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateToString = SimpleDateFormat("MMMM-dd-yyyy", Locale.getDefault())
    return try {
        val d = stringToDate.parse(this)
        val newDateString = dateToString.format(d)
        newDateString
    } catch (e: Exception) {
        ""
    }
}