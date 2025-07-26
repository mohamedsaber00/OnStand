package com.eid.onstand.feature.backgrounds.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.SolidBackgroundEffect
import com.eid.onstand.core.ui.theme.Colors

// Type-safe solid color background implementations

class BlackBackground : SolidBackgroundEffect() {
    override val displayName = "Black"
    override val previewColor = Colors.SolidBlack
    override val color = Colors.SolidBlack
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}

class WhiteBackground : SolidBackgroundEffect() {
    override val displayName = "White"
    override val previewColor = Colors.SolidWhite
    override val color = Colors.SolidWhite
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}

class GrayBackground : SolidBackgroundEffect() {
    override val displayName = "Gray"
    override val previewColor = Color.Gray
    override val color = Color.Gray
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}

class DarkGrayBackground : SolidBackgroundEffect() {
    override val displayName = "Dark Gray"
    override val previewColor = Color.DarkGray
    override val color = Color.DarkGray
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}

class BlueBackground : SolidBackgroundEffect() {
    override val displayName = "Blue"
    override val previewColor = Colors.SolidBlue
    override val color = Colors.SolidBlue
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}

class GreenBackground : SolidBackgroundEffect() {
    override val displayName = "Green"
    override val previewColor = Colors.SolidGreen
    override val color = Colors.SolidGreen
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}

class PurpleSolidBackground : SolidBackgroundEffect() {
    override val displayName = "Purple"
    override val previewColor = Colors.SolidPurple
    override val color = Colors.SolidPurple
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}

class RedSolidBackground : SolidBackgroundEffect() {
    override val displayName = "Red"
    override val previewColor = Colors.SolidRed
    override val color = Colors.SolidRed
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}