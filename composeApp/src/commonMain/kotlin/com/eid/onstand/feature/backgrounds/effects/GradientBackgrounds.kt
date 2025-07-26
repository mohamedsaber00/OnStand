package com.eid.onstand.feature.backgrounds.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.GradientBackgroundEffect
import com.eid.onstand.core.ui.theme.Colors
import com.eid.onstand.core.ui.theme.GradientColors

// Type-safe gradient background implementations

class SunsetGradient : GradientBackgroundEffect() {
    override val displayName = "Sunset"
    override val previewColor = Colors.SolidRed
    override val colors = GradientColors.DEFAULT_GRADIENT_COLORS
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.linearGradient(colors))
        )
    }
}

class OceanGradient : GradientBackgroundEffect() {
    override val displayName = "Ocean"
    override val previewColor = Colors.AccentBlue
    override val colors = GradientColors.GREEN_BLUE
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.linearGradient(colors))
        )
    }
}

class ForestGradient : GradientBackgroundEffect() {
    override val displayName = "Forest"
    override val previewColor = Colors.AccentGreen
    override val colors = GradientColors.GREEN_BLUE
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.linearGradient(colors))
        )
    }
}

class PurpleGradient : GradientBackgroundEffect() {
    override val displayName = "Purple"
    override val previewColor = Colors.AccentPurple
    override val colors = GradientColors.PURPLE_BLUE
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.linearGradient(colors))
        )
    }
}

class FireGradient : GradientBackgroundEffect() {
    override val displayName = "Fire"
    override val previewColor = Colors.AccentOrange
    override val colors = GradientColors.YELLOW_RED
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.linearGradient(colors))
        )
    }
}

class MintGradient : GradientBackgroundEffect() {
    override val displayName = "Mint"
    override val previewColor = Colors.AccentTeal
    override val colors = GradientColors.TEAL_CYAN
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.linearGradient(colors))
        )
    }
}