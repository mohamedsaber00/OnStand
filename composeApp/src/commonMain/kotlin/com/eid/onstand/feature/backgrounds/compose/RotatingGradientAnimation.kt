package com.eid.onstand.feature.backgrounds.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RotatingGradientBackground() {
    var time by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16) // ~60 FPS
            time += 0.016f
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val angle = time * 0.5f
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = maxOf(size.width, size.height) / 2f

        val startX = centerX + cos(angle) * radius
        val startY = centerY + sin(angle) * radius
        val endX = centerX - cos(angle) * radius
        val endY = centerY - sin(angle) * radius

        val shader = LinearGradientShader(
            from = Offset(startX, startY),
            to = Offset(endX, endY),
            colors = listOf(
                Color(0.1f, 0.4f, 1.0f, 1.0f),
                Color(0.8f, 0.9f, 1.0f, 1.0f)
            )
        )

        drawRect(brush = ShaderBrush(shader))
    }
}