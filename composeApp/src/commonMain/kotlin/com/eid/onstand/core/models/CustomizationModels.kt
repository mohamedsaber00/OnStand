package com.eid.onstand.core.models

import androidx.compose.ui.graphics.Color

data class BackgroundOption(
    val id: String,
    val type: BackgroundType,
    val name: String,
    val previewColor: Color? = null,
    val gradientColors: List<Color> = emptyList(),
    val imageUrl: String? = null,
    val isLive: Boolean = false
)

enum class BackgroundType {
    SOLID_COLOR,
    GRADIENT,
    ABSTRACT,
    LIVE,
    FOG
}

data class ClockStyle(
    val id: String,
    val name: String,
    val fontFamily: String,
    val isDigital: Boolean = true,
    val showSeconds: Boolean = false,
    val showDate: Boolean = true,
    val timeFormat: TimeFormat = TimeFormat.TWELVE_HOUR
)

enum class TimeFormat {
    TWELVE_HOUR,
    TWENTY_FOUR_HOUR
}

data class FontColorOption(
    val id: String,
    val name: String,
    val primaryColor: Color,
    val secondaryColor: Color? = null,
    val style: FontStyle = FontStyle.MODERN
)

enum class FontStyle {
    MODERN,
    CLASSIC,
    BOLD,
    ELEGANT
}

data class LayoutOption(
    val id: String,
    val name: String,
    val showPreviousMinutes: Boolean = true,
    val alignment: TimeAlignment = TimeAlignment.CENTER
)

enum class TimeAlignment {
    CENTER,
    LEFT,
    RIGHT
}

data class CustomizationState(
    val selectedBackground: BackgroundOption? = null,
    val selectedClockStyle: ClockStyle? = null,
    val selectedFontColor: FontColorOption? = null,
    val selectedLayout: LayoutOption? = null
)