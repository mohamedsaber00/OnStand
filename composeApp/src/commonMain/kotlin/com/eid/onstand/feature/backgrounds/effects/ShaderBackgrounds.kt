package com.eid.onstand.feature.backgrounds.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.ShaderBackgroundEffect
import com.eid.onstand.core.ui.theme.Colors
import com.eid.onstand.core.models.ShaderType
import com.eid.onstand.core.ui.shaders.shaderBackground
import com.eid.onstand.feature.backgrounds.shader.*

// Type-safe shader background implementations

class EtherBackground : ShaderBackgroundEffect() {
    override val displayName = "Ether"
    override val previewColor = Colors.AccentBlue
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
    override val previewColor = Colors.BackgroundSecondary
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
    override val previewColor = Colors.PrimaryVariant
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
    override val previewColor = Colors.SolidRed
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
    override val previewColor = Colors.Primary
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
    override val previewColor = Colors.AccentPurple
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
    override val previewColor = Colors.BackgroundPrimary
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
    override val previewColor = Colors.AccentBlue
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
    override val previewColor = Colors.AccentPurple
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