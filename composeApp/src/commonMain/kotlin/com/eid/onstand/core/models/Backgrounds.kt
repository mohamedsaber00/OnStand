package com.eid.onstand.core.models

import com.eid.onstand.feature.backgrounds.effects.*

/**
 * Simple list of all available backgrounds.
 */
object Backgrounds {
    val all: List<BackgroundEffect> by lazy {
        listOf(
            // Shaders
            EtherBackground(),
            SpaceBackground(),
            PaletteBackground(),
            RedShaderBackground(),
            GlowingRingBackground(),
            PurpleFlowBackground(),
            TrianglesBackground(),
            MovingWavesBackground(),
            TurbulenceBackground(),
            // Animations
            RotatingGradientAnimated(),
            FogEffectAnimated(),
            WavesAnimated()
        )
    }

    val default: BackgroundEffect get() = all.first()

    fun getByTypeId(typeId: String): BackgroundEffect? =
        all.find { it.typeId == typeId }
}
