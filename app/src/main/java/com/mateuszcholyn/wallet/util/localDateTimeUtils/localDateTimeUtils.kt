package com.mateuszcholyn.wallet.util.localDateTimeUtils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

private val simpleDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
private val textDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
private val monthAndYearDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MM.yyyy")

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, simpleDateFormat)

fun LocalDateTime.toHumanDateTimeText(): String =
    simpleDateFormat.format(this)

fun LocalDateTime.toHumanDateText(): String =
    textDateFormat.format(this)

fun LocalDateTime.toHumanMonthAndYear(): String =
    monthAndYearDateFormat.format(this)


fun Long.toInstant(): Instant =
    Instant.ofEpochMilli(this)

fun Instant.plusIntDays(days: Int): Instant =
    this.plus(days.toLong(), ChronoUnit.DAYS)

fun Instant.minusIntDays(days: Int): Instant =
    this.minusDays(days.toLong())

fun today(): Instant =
    Instant.now()

fun Instant.minusDays(days: Long): Instant =
    this.fromUTCInstantToUserLocalTimeZone()
        .minusDays(days)
        .fromUserLocalTimeZoneToUTCInstant()

fun Instant.plusDays(days: Long): Instant =
    this.fromUTCInstantToUserLocalTimeZone()
        .plusDays(days)
        .fromUserLocalTimeZoneToUTCInstant()

fun Instant.atStartOfTheDay(): Instant =
    fromUTCInstantToUserLocalTimeZone()
        .atStartOfTheDay()
        .fromUserLocalTimeZoneToUTCInstant()

fun LocalDateTime.atStartOfTheDay(): LocalDateTime =
    LocalDateTime.of(this.toLocalDate(), LocalTime.MIN)

fun Instant.atStartOfTheMonth(): Instant =
    fromUTCInstantToUserLocalTimeZone()
        .atStartOfTheMonth()
        .fromUserLocalTimeZoneToUTCInstant()

fun LocalDateTime.atStartOfTheMonth(): LocalDateTime =
    LocalDateTime.of(LocalDate.of(this.year, this.monthValue, 1), LocalTime.MIN)

fun LocalDateTime.atEndOfTheDay(): LocalDateTime =
    LocalDateTime.of(this.toLocalDate(), LocalTime.MAX)

fun Instant.atEndOfTheDay(): Instant =
    fromUTCInstantToUserLocalTimeZone()
        .atEndOfTheDay()
        .fromUserLocalTimeZoneToUTCInstant()

fun Instant.fromUTCInstantToUserLocalTimeZone(): LocalDateTime =
    LocalDateTime.ofInstant(this, userZone())

fun LocalDateTime.fromUserLocalTimeZoneToUTCInstant(): Instant =
    this
        .toInstant(
            userZone().rules.getOffset(
                Instant.now(),
            )
        )

private fun userZone(): ZoneId =
    ZoneId.systemDefault()