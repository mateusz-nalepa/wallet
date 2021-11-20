package com.mateuszcholyn.wallet.util

import android.text.Editable
import android.widget.TextView
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val simpleDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

fun TextView.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(text.toString(), simpleDateFormat)
}

fun LocalDateTime.toEditable(): Editable {
    return simpleDateFormat.format(this).toEditable()
}

fun LocalDateTime.toHumanText(): String {
    return simpleDateFormat.format(this)
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDateTime();
}

fun LocalDateTime.toMillis(): Long {
    return this.toInstant(ZoneOffset.UTC).toEpochMilli()
}

fun oneWeekAgo(): LocalDateTime {
    return LocalDateTime.now().minusDays(7)
}

fun LocalDateTime.atStartOfTheDay(): LocalDateTime {
    return LocalDateTime.of(this.toLocalDate(), LocalTime.MIN)
}

fun oneWeekAgoAsString(): String {
    return simpleDateFormat.format(oneWeekAgo())
}

fun currentDateAsString(): String {
    return simpleDateFormat.format(LocalDateTime.now())
}

val minDate: LocalDateTime = LocalDateTime.of(1970, 1, 1, 1, 1, 1)
val maxDate: LocalDateTime = LocalDateTime.of(3000, 12, 31, 23, 59, 59)