package com.eid.onstand

import kotlinx.datetime.LocalDateTime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform