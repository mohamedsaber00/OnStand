package com.eid.onstand.feature.backgrounds.effects

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.AnimatedBackgroundEffect
import com.eid.onstand.core.ui.theme.Colors
import com.eid.onstand.core.models.LiveAnimationType
import com.eid.onstand.feature.backgrounds.compose.FoggyBackground
import com.eid.onstand.feature.backgrounds.compose.RotatingGradientBackground
import com.eid.onstand.feature.backgrounds.compose.WaveBackground

// Type-safe animated background implementations

class RotatingGradientAnimated : AnimatedBackgroundEffect() {
    override val displayName = "Rotating"
    override val previewColor = Colors.Primary
    override val animationType = LiveAnimationType.ROTATING_GRADIENT
    
    @Composable
    override fun Render(modifier: Modifier) {
        RotatingGradientBackground()
    }
}

class FogEffectAnimated : AnimatedBackgroundEffect() {
    override val displayName = "Fog"
    override val previewColor = Colors.BackgroundPrimary
    override val animationType = LiveAnimationType.FOG_EFFECT
    
    @Composable
    override fun Render(modifier: Modifier) {
        FoggyBackground()
    }
}


class WavesAnimated : AnimatedBackgroundEffect() {
    override val displayName = "Waves"
    override val previewColor = Colors.AccentBlue
    override val animationType = LiveAnimationType.BREATHING_GLOW
    
    @Composable
    override fun Render(modifier: Modifier) {
        WaveBackground(modifier = modifier)
    }
}