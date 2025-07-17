package com.eid.onstand.core.models

import androidx.compose.ui.graphics.Color

sealed class BackgroundType {
    abstract val id: String
    abstract val name: String
    abstract val previewColor: Color

    data class Solid(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val color: Color
    ) : BackgroundType()

    data class Gradient(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val colors: List<Color>,
        val direction: GradientDirection = GradientDirection.VERTICAL
    ) : BackgroundType()

    data class Shader(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val shaderType: ShaderType,
        val speed: Float = 1.0f
    ) : BackgroundType()

    data class Live(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val animationType: LiveAnimationType,
        val animationSpeed: Float = 1.0f
    ) : BackgroundType()

    data class Pattern(
        override val id: String,
        override val name: String,
        override val previewColor: Color,
        val patternType: PatternType,
        val primaryColor: Color,
        val secondaryColor: Color? = null
    ) : BackgroundType()
}

enum class GradientDirection {
    VERTICAL,
    HORIZONTAL,
    DIAGONAL_UP,
    DIAGONAL_DOWN,
    RADIAL
}

enum class ShaderType {
    ETHER,
    GLOWING_RING,
    MOVING_TRIANGLES,
    PURPLE_GRADIENT,
    SPACE,
    MOVING_WAVES,
    TURBULENCE
}

enum class LiveAnimationType {
    ROTATING_GRADIENT,
    ANIMATED_PARTICLES,
    FOG_EFFECT,
    BREATHING_GLOW
}

enum class PatternType {
    GEOMETRIC,
    ORGANIC,
    MINIMAL,
    ARTISTIC
}

// Legacy support - will be removed gradually
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

sealed class ClockType {
    abstract val id: String
    abstract val name: String
    abstract val fontFamily: String
    abstract val showDate: Boolean
    abstract val timeFormat: TimeFormat

    data class Digital(
        override val id: String,
        override val name: String,
        override val fontFamily: String = "Roboto",
        override val showDate: Boolean = true,
        override val timeFormat: TimeFormat = TimeFormat.TWELVE_HOUR,
        val showSeconds: Boolean = false
    ) : ClockType()

    data class DigitalSegments(
        override val id: String,
        override val name: String,
        override val fontFamily: String = "Roboto",
        override val showDate: Boolean = false,
        override val timeFormat: TimeFormat = TimeFormat.TWELVE_HOUR,
        val segmentStyle: SegmentStyle = SegmentStyle.CLASSIC
    ) : ClockType()

    data class Analog(
        override val id: String,
        override val name: String,
        override val fontFamily: String = "Serif",
        override val showDate: Boolean = false,
        override val timeFormat: TimeFormat = TimeFormat.TWELVE_HOUR,
        val handStyle: HandStyle = HandStyle.CLASSIC,
        val showNumbers: Boolean = true
    ) : ClockType()

    data class Flip(
        override val id: String,
        override val name: String,
        override val fontFamily: String = "Monospace",
        override val showDate: Boolean = true,
        override val timeFormat: TimeFormat = TimeFormat.TWELVE_HOUR,
        val showSeconds: Boolean = true,
        val flipStyle: FlipStyle = FlipStyle.CLASSIC
    ) : ClockType()

    data class Minimal(
        override val id: String,
        override val name: String,
        override val fontFamily: String = "Helvetica",
        override val showDate: Boolean = false,
        override val timeFormat: TimeFormat = TimeFormat.TWELVE_HOUR,
        val showSeconds: Boolean = false
    ) : ClockType()
}

enum class SegmentStyle {
    CLASSIC,
    MODERN,
    ROUNDED
}

enum class HandStyle {
    CLASSIC,
    MODERN,
    ORNATE
}

enum class FlipStyle {
    CLASSIC,
    MODERN,
    MORPH
}

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
    val selectedBackground: BackgroundType? = null,
    val selectedClockType: ClockType? = null,
    val selectedFontColor: FontColorOption? = null,
    val selectedLayout: LayoutOption? = null
)