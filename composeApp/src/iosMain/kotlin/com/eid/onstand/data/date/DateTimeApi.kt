package com.eid.onstand.data.date

import kotlinx.datetime.LocalDateTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual fun getDayOfWeek(localDateTime: LocalDateTime): String {
    val dateComponents = NSDateComponents()
    dateComponents.year = localDateTime.year.toLong()
    dateComponents.month = localDateTime.monthNumber.toLong()
    dateComponents.day = localDateTime.dayOfMonth.toLong()
    dateComponents.hour = localDateTime.hour.toLong()
    dateComponents.minute = localDateTime.minute.toLong()
    dateComponents.second = localDateTime.second.toLong()

    val calendar = NSCalendar.currentCalendar
    val date = calendar.dateFromComponents(dateComponents) ?: NSDate()

    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = "EEEE"
    dateFormatter.locale = NSLocale.currentLocale
    return dateFormatter.stringFromDate(date)
}