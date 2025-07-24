package com.eid.onstand.feature.backgrounds.effects

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.AnimatedBackgroundEffect
import com.eid.onstand.core.models.LiveAnimationType
import com.eid.onstand.feature.backgrounds.compose.AnimatedBackground
import com.eid.onstand.feature.backgrounds.compose.FoggyBackground
import com.eid.onstand.feature.backgrounds.compose.RotatingGradientBackground

// Type-safe animated background implementations

class RotatingGradientAnimated : AnimatedBackgroundEffect() {
    override val displayName = "Rotating"
    override val previewColor = Color(0xFF7B68EE)
    override val animationType = LiveAnimationType.ROTATING_GRADIENT
    
    @Composable
    override fun Render(modifier: Modifier) {
        RotatingGradientBackground()
    }
}

class FogEffectAnimated : AnimatedBackgroundEffect() {
    override val displayName = "Fog"
    override val previewColor = Color(0xFF2C3E40)
    override val animationType = LiveAnimationType.FOG_EFFECT
    
    @Composable
    override fun Render(modifier: Modifier) {
        FoggyBackground()
    }
}

class ParticleAnimated : AnimatedBackgroundEffect() {
    override val displayName = "Particles"
    override val previewColor = Color(0xFF4A90E2)
    override val animationType = LiveAnimationType.ANIMATED_PARTICLES
    
    @Composable
    override fun Render(modifier: Modifier) {
        AnimatedBackground()
    }
}

class WavesAnimated : AnimatedBackgroundEffect() {
    override val displayName = "Waves"
    override val previewColor = Color(0xFF4A90E2)
    override val animationType = LiveAnimationType.BREATHING_GLOW
    
    @Composable
    override fun Render(modifier: Modifier) {
        // For now, use the same as particles - we can implement actual waves later
        AnimatedBackground()
    }
}