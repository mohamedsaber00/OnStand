package com.eid.onstand.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint

object BlurConstants {

    // Haze blur values
    val MIN_BLUR_RADIUS = 20.dp
    val DEFAULT_NOISE_FACTOR = 0.1f
    val DEFAULT_HAZE_TINT_ALPHA = 0.20f

    // Minimum blur haze style
    val MIN_BLUR_HAZE_STYLE = HazeStyle(
        backgroundColor = Color.Transparent,
        tint = HazeTint(Color.White.copy(alpha = DEFAULT_HAZE_TINT_ALPHA)),
        blurRadius = MIN_BLUR_RADIUS,
        noiseFactor = DEFAULT_NOISE_FACTOR
    )
}