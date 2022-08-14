package com.mateuszcholyn.wallet.util.localDateTimeUtils

import java.time.*
import java.time.format.DateTimeFormatter

private val simpleDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
private val textDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, simpleDateFormat)

fun LocalDateTime.toHumanText(): String =
    simpleDateFormat.format(this)

fun LocalDateTime.toHumanDateText(): String =
    textDateFormat.format(this)

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDateTime();

fun LocalDateTime.toMillis(): Long =
    this.toInstant(ZoneOffset.UTC).toEpochMilli()

fun LocalDateTime.atStartOfTheDay(): LocalDateTime =
    LocalDateTime.of(this.toLocalDate(), LocalTime.MIN)

fun LocalDateTime.atEndOfTheDay(): LocalDateTime =
    LocalDateTime.of(this.toLocalDate(), LocalTime.MAX)

fun LocalDateTime.atStartOfTheMonth(): LocalDateTime =
    LocalDateTime.of(LocalDate.of(this.year, this.monthValue, 1), LocalTime.MIN)

fun LocalDateTime.plusIntDays(days: Int): LocalDateTime =
    this.plusDays(days.toLong())

fun LocalDateTime.minusIntDays(days: Int): LocalDateTime =
    this.minusDays(days.toLong())

fun today(): LocalDateTime =
    LocalDateTime.now()

fun oneWeekAgo(): LocalDateTime =
    LocalDateTime.now().minusIntDays(7)

