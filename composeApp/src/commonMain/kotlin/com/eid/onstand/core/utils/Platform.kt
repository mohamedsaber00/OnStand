package com.eid.onstand.core.utils

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform