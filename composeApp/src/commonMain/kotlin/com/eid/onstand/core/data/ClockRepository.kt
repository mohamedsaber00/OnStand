package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*

class ClockRepository {

    fun getClockTypes(): List<ClockType> = listOf(
        ClockType.Digital(
            name = "Digital"
        ),
        ClockType.DigitalSegments(
            name = "Digital Segments"
        ),
        ClockType.Analog(
            name = "Analog Classic"
        ),
        ClockType.Flip(
            name = "Flip Clock"
        ),
        ClockType.MorphFlip(
            name = "Morph Flip"
        )
    )

    fun getFontColorOptions(): List<FontColorOption> = listOf(
        FontColorOption(
            name = "Modern",
            primaryColor = Color.White,
            secondaryColor = Color(0xFFB0B0B0),
            style = FontStyle.MODERN
        ),
        FontColorOption(
            name = "Classic",
            primaryColor = Color.Black,
            secondaryColor = Color(0xFF666666),
            style = FontStyle.CLASSIC
        ),
        FontColorOption(
            name = "Bold Blue",
            primaryColor = Color(0xFF4A90E2),
            secondaryColor = Color(0xFF7B68EE),
            style = FontStyle.BOLD
        ),
        FontColorOption(
            name = "Elegant Gold",
            primaryColor = Color(0xFFFFD700),
            secondaryColor = Color(0xFFFFA500),
            style = FontStyle.ELEGANT
        ),
        FontColorOption(
            name = "Green Digital",
            primaryColor = Color(0xFF00FF00),
            secondaryColor = Color(0xFF32CD32),
            style = FontStyle.MODERN
        ),
        FontColorOption(
            name = "Purple Glow",
            primaryColor = Color(0xFF9B59B6),
            secondaryColor = Color(0xFF8E44AD),
            style = FontStyle.MODERN
        )
    )

}