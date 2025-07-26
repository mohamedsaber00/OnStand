package com.eid.onstand.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object GradientColors {

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
    
    // Screen Background Gradients
    val SCREEN_BACKGROUND = listOf(
        Color(0xFF2C2C2C),
        Color(0xFF1A1A1A)
    )
    
    // Overlay Gradients
    val BACKGROUND_OVERLAY = listOf(
        Color.Transparent,
        Color.Black.copy(alpha = 0.6f)
    )
    
    // Gradient Background Effects
    val PURPLE_BLUE = listOf(
        Color(0xFF8B5CF6),
        Color(0xFF3B82F6)
    )
    
    val PINK_ORANGE = listOf(
        Color(0xFFEC4899),
        Color(0xFFF97316)
    )
    
    val GREEN_BLUE = listOf(
        Color(0xFF10B981),
        Color(0xFF3B82F6)
    )
    
    val YELLOW_RED = listOf(
        Color(0xFFFBBF24),
        Color(0xFFEF4444)
    )
    
    val INDIGO_PURPLE = listOf(
        Color(0xFF6366F1),
        Color(0xFF8B5CF6)
    )
    
    val TEAL_CYAN = listOf(
        Color(0xFF14B8A6),
        Color(0xFF06B6D4)
    )

    // Common gradients
    val TRANSPARENT_GRADIENT = Brush.linearGradient(
        listOf(Color.Transparent, Color.Transparent)
    )

    val BLACK_GRADIENT = Brush.linearGradient(
        listOf(Color.Black, Color.Black)
    )
}

@Deprecated("Use Colors object instead", ReplaceWith("Colors"))
object ColorConstants {

    // Default colors - moved to Colors object
    val DEFAULT_CARD_COLOR = Colors.OverlayBlack
    val DEFAULT_MORPH_CARD_COLOR = Colors.MorphCardDefault
    val DEFAULT_TEXT_COLOR = Colors.TextPrimary
    val DEFAULT_INACTIVE_COLOR_ALPHA = 0.011f
    val DEFAULT_NUMBERS_COLOR_ALPHA = 0.8f

    // Transparency values
    val TRANSPARENT = Colors.Transparent
}