package com.eid.onstand.feature.backgrounds.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.ShaderBackgroundEffect
import com.eid.onstand.core.models.ShaderType
import com.eid.onstand.core.ui.shaders.shaderBackground
import com.eid.onstand.feature.backgrounds.shader.*

// Type-safe shader background implementations

class EtherBackground : ShaderBackgroundEffect() {
    override val displayName = "Ether"
    override val previewColor = Color(0xFF4A90E2)
    override val shaderType = ShaderType.ETHER
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(EtherShader)
        )
    }
}

class SpaceBackground : ShaderBackgroundEffect() {
    override val displayName = "Space"
    override val previewColor = Color(0xFF1A0033)
    override val shaderType = ShaderType.SPACE
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(SpaceShader)
        )
    }
}

class PaletteBackground : ShaderBackgroundEffect() {
    override val displayName = "Palette"
    override val previewColor = Color(0xFF5A4FCF)
    override val shaderType = ShaderType.PALETTE
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(PaletteShader)
        )
    }
}

class RedShaderBackground : ShaderBackgroundEffect() {
    override val displayName = "Red"
    override val previewColor = Color(0xFFB83C5E)
    override val shaderType = ShaderType.RED
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(RedShader)
        )
    }
}

class GlowingRingBackground : ShaderBackgroundEffect() {
    override val displayName = "Glowing Ring"
    override val previewColor = Color(0xFF7B68EE)
    override val shaderType = ShaderType.GLOWING_RING
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(GlowingRing)
        )
    }
}

class PurpleFlowBackground : ShaderBackgroundEffect() {
    override val displayName = "Purple Flow"
    override val previewColor = Color(0xFF9B59B6)
    override val shaderType = ShaderType.PURPLE_GRADIENT
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(PurpleGradientShader)
        )
    }
}

class TrianglesBackground : ShaderBackgroundEffect() {
    override val displayName = "Triangles"
    override val previewColor = Color(0xFF2C3E50)
    override val shaderType = ShaderType.MOVING_TRIANGLES
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(MovingTrianglesShader)
        )
    }
}

class MovingWavesBackground : ShaderBackgroundEffect() {
    override val displayName = "Moving Waves"
    override val previewColor = Color(0xFF4A90E2)
    override val shaderType = ShaderType.MOVING_WAVES
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(MovingWaveShader)
        )
    }
}

class TurbulenceBackground : ShaderBackgroundEffect() {
    override val displayName = "Turbulence"
    override val previewColor = Color(0xFF8E44AD)
    override val shaderType = ShaderType.PURPLE_SMOKE
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .shaderBackground(PurpleSmokeShader)
        )
    }
}