package com.eid.onstand.feature.backgrounds.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.SolidBackgroundEffect

// Type-safe solid color background implementations

class BlackBackground : SolidBackgroundEffect() {
    override val displayName = "Black"
    override val previewColor = Color.Black
    override val color = Color.Black
    
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
    override val previewColor = Color.White
    override val color = Color.White
    
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
    override val previewColor = Color(0xFF2196F3)
    override val color = Color(0xFF2196F3)
    
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
    override val previewColor = Color(0xFF4CAF50)
    override val color = Color(0xFF4CAF50)
    
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
    override val previewColor = Color(0xFF9C27B0)
    override val color = Color(0xFF9C27B0)
    
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
    override val previewColor = Color(0xFFF44336)
    override val color = Color(0xFFF44336)
    
    @Composable
    override fun Render(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        )
    }
}