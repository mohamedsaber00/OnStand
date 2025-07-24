package com.eid.onstand.feature.backgrounds.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.GradientBackgroundEffect

// Type-safe gradient background implementations

class SunsetGradient : GradientBackgroundEffect() {
    override val displayName = "Sunset"
    override val previewColor = Color(0xFFFF6B6B)
    override val colors = listOf(
        Color(0xFF4A90E2),
        Color(0xFF7B68EE),
        Color(0xFFFF6B6B)
    )
    
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
    override val previewColor = Color(0xFF4A90E2)
    override val colors = listOf(
        Color(0xFF000428),
        Color(0xFF004e92)
    )
    
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
    override val previewColor = Color(0xFF134E5E)
    override val colors = listOf(
        Color(0xFF134E5E),
        Color(0xFF71B280)
    )
    
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
    override val previewColor = Color(0xFF667eea)
    override val colors = listOf(
        Color(0xFF667eea),
        Color(0xFF764ba2)
    )
    
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
    override val previewColor = Color(0xFFf12711)
    override val colors = listOf(
        Color(0xFFf12711),
        Color(0xFFf5af19)
    )
    
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
    override val previewColor = Color(0xFF00b4db)
    override val colors = listOf(
        Color(0xFF00b4db),
        Color(0xFF0083b0)
    )
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.linearGradient(colors))
        )
    }
}