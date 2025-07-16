package com.eid.onstand.data.date

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

actual fun getDayOfWeek(localDateTime: LocalDateTime): String {
    val calendar = Calendar.getInstance()
    calendar.set(
        localDateTime.year,
        localDateTime.monthNumber - 1, // Calendar months are 0-based
        localDateTime.dayOfMonth,
        localDateTime.hour,
        localDateTime.minute,
        localDateTime.second
    )
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)
}