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
        ClockType.MorphFlip(
            name = "Morph Flip"
        )
    )

    fun getFontFamilies(): List<FontFamily> = listOf(
        FontFamily.ROBOTO,
        FontFamily.SANS_SERIF,
        FontFamily.SERIF,
        FontFamily.MONOSPACE
    )

    fun getClockColors(): List<ClockColor> = listOf(
        ClockColor(name = "White", color = Color.White),
        ClockColor(name = "Black", color = Color.Black),
        ClockColor(name = "Blue", color = Color(0xFF2196F3)),
        ClockColor(name = "Green", color = Color(0xFF4CAF50)),
        ClockColor(name = "Red", color = Color(0xFFF44336)),
        ClockColor(name = "Purple", color = Color(0xFF9C27B0)),
        ClockColor(name = "Orange", color = Color(0xFFFF9800)),
        ClockColor(name = "Cyan", color = Color(0xFF00BCD4)),
        ClockColor(name = "Pink", color = Color(0xFFE91E63)),
        ClockColor(name = "Yellow", color = Color(0xFFFFEB3B)),
        ClockColor(name = "Indigo", color = Color(0xFF3F51B5)),
        ClockColor(name = "Teal", color = Color(0xFF009688)),
        ClockColor(name = "Lime", color = Color(0xFFCDDC39)),
        ClockColor(name = "Deep Orange", color = Color(0xFFFF5722)),
        ClockColor(name = "Brown", color = Color(0xFF795548)),
        ClockColor(name = "Gray", color = Color(0xFF9E9E9E)),
        ClockColor(name = "Blue Gray", color = Color(0xFF607D8B)),
        ClockColor(name = "Light Blue", color = Color(0xFF03A9F4)),
        ClockColor(name = "Light Green", color = Color(0xFF8BC34A)),
        ClockColor(name = "Amber", color = Color(0xFFFFC107))
    )

}