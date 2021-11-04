package com.mateuszcholyn.wallet.util

import android.text.Editable
import android.widget.TextView
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.view.expense.ALL_CATEGORIES
import org.joda.time.LocalDateTime
import java.text.SimpleDateFormat
import java.util.*

val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())


//TODO - refactor
// from TextView -> Calendar -> LocalDateTime
// to TextView -> LocalDateTime
fun TextView.toLocalDateTime(): LocalDateTime {
    val stringDate = this.text.toString()
    val parsedDate = simpleDateFormat.parse(stringDate)
    val gregorianCalendar = GregorianCalendar.getInstance()
    gregorianCalendar.time = parsedDate

    return LocalDateTime.fromCalendarFields(gregorianCalendar)
}

fun dateAsString(calendar: Calendar) =
    simpleDateFormat.format(calendar.time)


fun LocalDateTime.toEditable(): Editable {
    val calendar = Calendar.getInstance()
    calendar.time = this.toDate()
    return simpleDateFormat.format(calendar.time).toEditable()
}

fun LocalDateTime.toTextForEditable(): String {
    val gregorianCalendar = GregorianCalendar()
    gregorianCalendar.time = this.toDate()

    return simpleDateFormat.format(gregorianCalendar.time)
}

fun defaultSearchCriteria(): ExpenseSearchCriteria {
    return ExpenseSearchCriteria(
        categoryName = ALL_CATEGORIES,
        beginDate = minDate,
        endDate = maxDate
    )
}

fun findEarliest(resultList: List<Expense>): LocalDateTime {
    return resultList
        .map { it.date }
        .sorted()
        .first()
}

fun findLatest(resultList: List<Expense>): LocalDateTime {
    return resultList
        .map { it.date }
        .sortedDescending()
        .first()
}


fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime(this)
}

fun LocalDateTime.toMillis(): Long {
    return this.toDateTime().millis
}

fun currentCalendarAsString(): String {
    return simpleDateFormat.format(GregorianCalendar().time)
}

val minDate = LocalDateTime(1970, 1, 1, 1, 1, 1)
val maxDate = LocalDateTime(3000, 12, 31, 23, 59, 59)