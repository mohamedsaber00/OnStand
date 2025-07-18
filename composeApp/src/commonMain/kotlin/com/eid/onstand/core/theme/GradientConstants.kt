package com.eid.onstand.core.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object GradientConstants {

    // Default gradient colors
    val DEFAULT_GRADIENT_COLORS = listOf(
        Color(0xFF4A90E2),
        Color(0xFF7B68EE),
        Color(0xFF9B59B6)
    )

    // Common gradients
    val TRANSPARENT_GRADIENT = Brush.linearGradient(
        listOf(Color.Transparent, Color.Transparent)
    )

    val BLACK_GRADIENT = Brush.linearGradient(
        listOf(Color.Black, Color.Black)
    )
}

object ColorConstants {

    // Default colors
    val DEFAULT_CARD_COLOR = Color.Black.copy(alpha = 0.6f)
    val DEFAULT_MORPH_CARD_COLOR = Color(0xFFFFA77A).copy(alpha = 0.85f)
    val DEFAULT_TEXT_COLOR = Color.White
    val DEFAULT_INACTIVE_COLOR_ALPHA = 0.1f
    val DEFAULT_NUMBERS_COLOR_ALPHA = 0.8f

    // Transparency values
    val TRANSPARENT = Color.Transparent
}