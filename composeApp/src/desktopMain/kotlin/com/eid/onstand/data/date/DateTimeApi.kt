package com.eid.onstand.data.date

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

actual fun getDayOfWeek(localDateTime: LocalDateTime): String {
    val date = Date.from(
        localDateTime.toJavaLocalDateTime().atZone(TimeZone.getDefault().toZoneId()).toInstant()
    )
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
}
