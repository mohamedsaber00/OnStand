package com.eid.onstand.core.utils

import kotlin.math.pow
import kotlin.math.round

fun Float.round(decimals: Int): Float {
    val factor = 10.0.pow(decimals).toFloat()
    return round(this * factor) / factor
}