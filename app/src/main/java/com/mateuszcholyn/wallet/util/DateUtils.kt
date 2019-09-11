package com.mateuszcholyn.wallet.util

import android.widget.TextView
import com.mateuszcholyn.wallet.domain.expense.activity.ALL_CATEGORIES
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseDto
import com.mateuszcholyn.wallet.domain.expense.model.ExpenseSearchCriteria
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

fun defaultSearchCriteria(): ExpenseSearchCriteria {

    val maxgc = GregorianCalendar()
    maxgc.time = Date(java.lang.Long.MAX_VALUE)

    val mingc = GregorianCalendar()
    mingc.time = Date(java.lang.Long.MIN_VALUE)

    return ExpenseSearchCriteria(
            categoryName = ALL_CATEGORIES,
            beginDate = mingc,
            endDate = maxgc
    )
}

fun findEarliest(resultList: List<ExpenseDto>): Calendar {
    return resultList
            .map { it.date }
            .sorted()
            .first()
}

fun findLatest(resultList: List<ExpenseDto>): Calendar {
    return resultList
            .map { it.date }
            .sortedDescending()
            .first()
}