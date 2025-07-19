package com.eid.onstand.core.models

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

sealed class BackgroundType {
    abstract val name: String
    abstract val previewColor: Color

    data class Solid(
        override val name: String,
        override val previewColor: Color,
        val color: Color
    ) : BackgroundType()

    data class Gradient(
        override val name: String,
        override val previewColor: Color,
        val colors: List<Color>,
        val direction: GradientDirection = GradientDirection.VERTICAL
    ) : BackgroundType()

    data class Shader(
        override val name: String,
        override val previewColor: Color,
        val shaderType: ShaderType,
        val speed: Float = 1.0f
    ) : BackgroundType()

    data class Live(
        override val name: String,
        override val previewColor: Color,
        val animationType: LiveAnimationType,
        val animationSpeed: Float = 1.0f
    ) : BackgroundType()

    data class Pattern(
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
    PALETTE,
    RED,
    MOVING_WAVES,
    PURPLE_SMOKE
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
    abstract val name: String
    abstract val previewColor: Color

    data class SolidColor(
        override val name: String,
        override val previewColor: Color,
        val color: Color
    ) : BackgroundOption()

    data class Gradient(
        override val name: String,
        override val previewColor: Color,
        val colors: List<Color>,
        val angle: Float = 0f
    ) : BackgroundOption()

    data class Abstract(
        override val name: String,
        override val previewColor: Color,
        val patternType: AbstractPatternType
    ) : BackgroundOption()

    data class Live(
        override val name: String,
        override val previewColor: Color,
        val animationType: LiveAnimationType
    ) : BackgroundOption()

    data class Shader(
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
    abstract val name: String
    abstract val isDigital: Boolean
    abstract val showSeconds: Boolean

    data class Digital(
        override val name: String,
        override val isDigital: Boolean = true,
        override val showSeconds: Boolean = false
    ) : ClockType()

    data class DigitalSegments(
        override val name: String,
        override val isDigital: Boolean = true,
        override val showSeconds: Boolean = false
    ) : ClockType()

    data class Analog(
        override val name: String,
        override val isDigital: Boolean = false,
        override val showSeconds: Boolean = false
    ) : ClockType()

    data class MorphFlip(
        override val name: String,
        override val isDigital: Boolean = true,
        override val showSeconds: Boolean = false
    ) : ClockType()
}

enum class FontFamily(val displayName: String, val systemName: String) {
    ROBOTO("Roboto", "roboto"),
    SANS_SERIF("Sans Serif", "sans-serif"),
    SERIF("Serif", "serif"),
    MONOSPACE("Monospace", "monospace")
}

data class ClockColor(
    val name: String,
    val color: Color
)

data class CustomizationState(
    val selectedBackground: BackgroundType? = null,
    val selectedClockType: ClockType? = null,
    val selectedFont: FontFamily? = null,
    val selectedColor: ClockColor? = null,
)

@Serializable
data class SerializableCustomizationState(
    val backgroundId: String? = null,
    val backgroundType: String? = null,
    val clockTypeId: String? = null,
    val clockTypeName: String? = null,
    val selectedFont: String? = null,
    val selectedColorName: String? = null,
)