package com.translator.uzbek.english.dictionary.core.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun currentTimestamp(): Long {
    return Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .toInstant(TimeZone.UTC)
        .toEpochMilliseconds()
}

fun Long.toDateTime(): DateTimeModel {
    return Instant.fromEpochMilliseconds(this).toDateTimeModel()
}

private fun Instant.toDateTimeModel(): DateTimeModel {
    return toLocalDateTime(TimeZone.UTC).toDateTimeModel()
}

private fun LocalDateTime.toDateTimeModel(): DateTimeModel {
    return DateTimeModel(
        dayOfMonth = dayOfMonth,
        month = monthNumber,
        monthName = month.name,
        weekName = dayOfWeek.name,
        year = year,
        hour = hour,
        minute = minute,
        second = second,
    )
}