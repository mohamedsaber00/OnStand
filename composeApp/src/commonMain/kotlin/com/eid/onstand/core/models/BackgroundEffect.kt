package com.eid.onstand.core.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Base class for all background effects.
 */
abstract class BackgroundEffect {
    abstract val displayName: String
    abstract val previewColor: Color

    @Composable
    abstract fun Render(modifier: Modifier = Modifier)

    val typeId: String get() = this::class.simpleName ?: "UnknownBackground"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BackgroundEffect) return false
        return this::class == other::class
    }

    override fun hashCode(): Int = this::class.hashCode()
}

/**
 * Shader-based background effect.
 */
abstract class ShaderBackgroundEffect : BackgroundEffect() {
    abstract val shaderType: ShaderType
}

/**
 * Animated background effect.
 */
abstract class AnimatedBackgroundEffect : BackgroundEffect() {
    abstract val animationType: LiveAnimationType
}

// Enums for background types
enum class ShaderType {
    ETHER, GLOWING_RING, MOVING_TRIANGLES, PURPLE_GRADIENT,
    SPACE, PALETTE, RED, MOVING_WAVES, PURPLE_SMOKE
}

enum class LiveAnimationType {
    ROTATING_GRADIENT, FOG_EFFECT, BREATHING_GLOW
}

// Font family enum
enum class FontFamily(val displayName: String, val systemName: String) {
    ROBOTO("Roboto", "roboto"),
    SERIF("Serif", "serif"),
    MONOSPACE("Monospace", "monospace"),
    CURSIVE("Cursive", "cursive")
}
