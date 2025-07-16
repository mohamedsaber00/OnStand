package com.eid.onstand.core.models

import androidx.compose.ui.graphics.Color

sealed class BackgroundOption {
    abstract val id: String
    abstract val name: String
    abstract val previewColor: Color

    data class SolidColor(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val color: Color
    ) : BackgroundOption()

    data class Gradient(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val colors: List<Color>,
        val angle: Float = 0f
    ) : BackgroundOption()

    data class Abstract(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val patternType: AbstractPatternType
    ) : BackgroundOption()

    data class Live(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val animationType: LiveAnimationType
    ) : BackgroundOption()

    data class Shader(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val shaderType: ShaderType
    ) : BackgroundOption()
}

enum class AbstractPatternType {
    GEOMETRIC,
    ORGANIC,
    MINIMAL,
    ARTISTIC
}

enum class LiveAnimationType {
    ROTATING_GRADIENT,
    ANIMATED_PARTICLES,
    FOG_EFFECT,
    BREATHING_GLOW
}

enum class ShaderType {
    ETHER,
    GLOWING_RING,
    MOVING_TRIANGLES,
    PURPLE_GRADIENT,
    SPACE
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