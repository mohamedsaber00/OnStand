package com.eid.onstand.core.modules

import com.eid.onstand.core.models.BackgroundEffect
import com.eid.onstand.core.models.BackgroundRegistry
import com.eid.onstand.core.models.ClockRegistry
import com.eid.onstand.core.models.ClockWidget
import com.eid.onstand.feature.backgrounds.effects.*
import com.eid.onstand.feature.widgets.clocks.effects.*

/**
 * Type-safe core module that registers all built-in backgrounds and clocks.
 * Uses class-based registration instead of string IDs.
 */
class CoreModule : FeatureModule {
    
    override fun registerBackgrounds(registry: BackgroundRegistry) {
        // Register shader backgrounds - each is a unique class
        registry.registerAll(listOf(
            EtherBackground(),
            SpaceBackground(),
            PaletteBackground(),
            RedShaderBackground(),
            GlowingRingBackground(),
            PurpleFlowBackground(),
            TrianglesBackground(),
            MovingWavesBackground(),
            TurbulenceBackground()
        ))
        
        // Register animated backgrounds
        registry.registerAll(listOf(
            RotatingGradientAnimated(),
            FogEffectAnimated(),
            WavesAnimated()
        ))
        
        // Register gradient backgrounds
        registry.registerAll(listOf(
            SunsetGradient(),
            OceanGradient(),
            ForestGradient(),
            PurpleGradient(),
            FireGradient(),
            MintGradient()
        ))
        
        // Register solid color backgrounds
        registry.registerAll(listOf(
            BlackBackground(),
            WhiteBackground(),
            GrayBackground(),
            DarkGrayBackground(),
            BlueBackground(),
            GreenBackground(),
            PurpleSolidBackground(),
            RedSolidBackground()
        ))
    }
    
    override fun registerClocks(registry: ClockRegistry) {
        // Register digital clocks
        registry.registerAll(listOf(
            BasicDigitalClock(),
        ))
        
        // Register analog clocks
        registry.registerAll(listOf(
            ClassicAnalogClock()
        ))
    }
}

// Extension functions for type-safe access
inline fun <reified T : BackgroundEffect> getBackground(): T? {
    return BackgroundRegistry.get<T>()
}

inline fun <reified T : ClockWidget> getClock(): T? {
    return ClockRegistry.get<T>()
}

// Usage examples:
// val spaceBackground = getBackground<SpaceBackground>()
// val digitalClock = getClock<BasicDigitalClock>()