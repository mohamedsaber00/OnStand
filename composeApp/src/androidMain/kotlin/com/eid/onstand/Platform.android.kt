package com.eid.onstand

import android.os.Build
import kotlinx.datetime.LocalDateTime
import java.text.SimpleDateFormat
import java.util.*

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
