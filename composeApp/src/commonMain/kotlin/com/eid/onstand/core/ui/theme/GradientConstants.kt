package com.eid.onstand.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object GradientConstants {

    // Default gradient colors
    val DEFAULT_GRADIENT_COLORS = listOf(
        Color(0xFF4A90E2),
        Color(0xFF7B68EE),
        Color(0xFF9B59B6)
    )

    // Animated background gradient colors - Apple liquid glass inspired
    val ANIMATED_BACKGROUND_COLORS = listOf(
        Color(0xFF007AFF).copy(alpha = 0.8f), // Apple blue
        Color(0xFF5856D6).copy(alpha = 0.7f), // Apple purple
        Color(0xFF32D74B).copy(alpha = 0.6f), // Apple green
        Color(0xFF00C7BE).copy(alpha = 0.7f), // Apple teal
        Color(0xFF64D2FF).copy(alpha = 0.8f), // Light blue
        Color(0xFFBF5AF2).copy(alpha = 0.6f)  // Light purple
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
    val DEFAULT_INACTIVE_COLOR_ALPHA = 0.011f
    val DEFAULT_NUMBERS_COLOR_ALPHA = 0.8f

    // Transparency values
    val TRANSPARENT = Color.Transparent
}