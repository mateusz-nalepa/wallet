package com.mateuszcholyn.wallet.util.dateutils

import java.time.*
import java.time.format.DateTimeFormatter

private val simpleDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
private val textDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, simpleDateFormat)
}

fun LocalDateTime.toHumanText(): String {
    return simpleDateFormat.format(this)
}

fun LocalDateTime.toHumanDateText(): String {
    return textDateFormat.format(this)
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDateTime();
}

fun LocalDateTime.toMillis(): Long {
    return this.toInstant(ZoneOffset.UTC).toEpochMilli()
}

fun LocalDateTime.atStartOfTheDay(): LocalDateTime {
    return LocalDateTime.of(this.toLocalDate(), LocalTime.MIN)
}

fun LocalDateTime.atEndOfTheDay(): LocalDateTime {
    return LocalDateTime.of(this.toLocalDate(), LocalTime.MAX)
}

fun LocalDateTime.atStartOfTheMonth(): LocalDateTime {
    return LocalDateTime.of(LocalDate.of(this.year, this.monthValue, 1), LocalTime.MIN)
}


val minDate: LocalDateTime = LocalDateTime.of(1970, 1, 1, 1, 1, 1)
val maxDate: LocalDateTime = LocalDateTime.of(3000, 12, 31, 23, 59, 59)

fun today(): LocalDateTime =
    LocalDateTime.now()

fun oneDayAgo(): LocalDateTime =
    LocalDateTime.now().minusDays(1)
