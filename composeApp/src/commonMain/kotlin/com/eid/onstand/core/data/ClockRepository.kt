package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*

class ClockRepository {

    fun getClockTypes(): List<ClockType> = listOf(
        ClockType.Digital(
            id = "digital_modern",
            name = "Digital"
        ),
        ClockType.DigitalSegments(
            id = "digital_segments",
            name = "Digital Segments"
        ),
        ClockType.Analog(
            id = "analog_classic",
            name = "Analog Classic"
        ),
        ClockType.Flip(
            id = "flip_clock",
            name = "Flip Clock"
        ),
        ClockType.MorphFlip(
            id = "morph_flip_clock",
            name = "Morph Flip"
        )
    )

    fun getFontColorOptions(): List<FontColorOption> = listOf(
        FontColorOption(
            id = "modern_white",
            name = "Modern",
            primaryColor = Color.White,
            secondaryColor = Color(0xFFB0B0B0),
            style = FontStyle.MODERN
        ),
        FontColorOption(
            id = "classic_black",
            name = "Classic",
            primaryColor = Color.Black,
            secondaryColor = Color(0xFF666666),
            style = FontStyle.CLASSIC
        ),
        FontColorOption(
            id = "bold_blue",
            name = "Bold Blue",
            primaryColor = Color(0xFF4A90E2),
            secondaryColor = Color(0xFF7B68EE),
            style = FontStyle.BOLD
        ),
        FontColorOption(
            id = "elegant_gold",
            name = "Elegant Gold",
            primaryColor = Color(0xFFFFD700),
            secondaryColor = Color(0xFFFFA500),
            style = FontStyle.ELEGANT
        ),
        FontColorOption(
            id = "green_digital",
            name = "Green Digital",
            primaryColor = Color(0xFF00FF00),
            secondaryColor = Color(0xFF32CD32),
            style = FontStyle.MODERN
        ),
        FontColorOption(
            id = "purple_glow",
            name = "Purple Glow",
            primaryColor = Color(0xFF9B59B6),
            secondaryColor = Color(0xFF8E44AD),
            style = FontStyle.MODERN
        )
    )

    fun getLayoutOptions(): List<LayoutOption> = listOf(
        LayoutOption(
            id = "previous_minutes",
            name = "Previous Minutes",
            showPreviousMinutes = true,
            alignment = TimeAlignment.CENTER
        ),
        LayoutOption(
            id = "clean_center",
            name = "Clean Center",
            showPreviousMinutes = false,
            alignment = TimeAlignment.CENTER
        ),
        LayoutOption(
            id = "left_aligned",
            name = "Left Aligned",
            showPreviousMinutes = true,
            alignment = TimeAlignment.LEFT
        ),
        LayoutOption(
            id = "right_aligned",
            name = "Right Aligned",
            showPreviousMinutes = false,
            alignment = TimeAlignment.RIGHT
        )
    )
}