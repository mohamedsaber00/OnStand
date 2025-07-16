package com.eid.onstand

import java.text.SimpleDateFormat
import java.util.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.util.Locale
import java.util.TimeZone

class DesktopPlatform : Platform {
    override val name: String = "Desktop"
}

actual fun getPlatform(): Platform = DesktopPlatform()

