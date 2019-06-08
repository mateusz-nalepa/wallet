package com.mateuszcholyn.wallet.util

import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

fun toDbDate(calendar: Calendar) =
        calendar.timeInMillis

fun fromDbDate(dbDate: Long) =
        Calendar.getInstance().apply { timeInMillis = dbDate }


fun dateAsGregorianCalendar(date: TextView): Calendar {
    val stringDate = date.text.toString()
    val parsedDate = simpleDateFormat.parse(stringDate)
    val gregorianCalendar = GregorianCalendar.getInstance()
    gregorianCalendar.time = parsedDate
    return gregorianCalendar
}

fun dateAsString(calendar: Calendar) =
        simpleDateFormat.format(calendar.time)
