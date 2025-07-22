package com.eid.onstand.feature.backgrounds.compose

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * A composable that displays a soft, animated fog background effect.
 */
@Composable
fun FoggyBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite fog animation")
    val progress1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 22000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fog_animation_1"
    )
    val progress2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 31000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fog_animation_2"
    )
    val progress3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 40000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fog_animation_3"
    )


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2C3E40))
            .clip(RoundedCornerShape(32.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawFogAnimation(progress1, progress2, progress3)
        }
    }
}

/**
 * Draws the animated fog effect by blending multiple, independently moving radial gradients.
 * This breaks the single-circle illusion and creates a more natural, layered fog.
 * @param progress1 The first animation progress value (0.0 to 1.0).
 * @param progress2 The second animation progress value (0.0 to 1.0).
 * @param progress3 The third animation progress value (0.0 to 1.0).
 */
private fun DrawScope.drawFogAnimation(progress1: Float, progress2: Float, progress3: Float) {
    val angle1A = progress1 * 2 * PI.toFloat()
    val angle1B = progress2 * 2 * PI.toFloat()
    val center1 = Offset(
        x = size.width * 0.3f + (cos(angle1A) * size.width * 0.4f),
        y = size.height * 0.4f + (sin(angle1B) * size.height * 0.5f)
    )
    val radius1 = size.width * (0.6f + 0.1f * sin(angle1B))

    val angle2A = progress2 * 2 * PI.toFloat()
    val angle2B = progress3 * 2 * PI.toFloat()
    val center2 = Offset(
        x = size.width * 0.7f + (cos(angle2A) * size.width * 0.35f),
        y = size.height * 0.6f + (sin(angle2B) * size.height * 0.4f)
    )
    val radius2 = size.width * (0.7f + 0.1f * cos(angle2A))

    val angle3A = progress3 * 2 * PI.toFloat()
    val angle3B = progress1 * 2 * PI.toFloat()
    val center3 = Offset(
        x = size.width * 0.5f + (cos(angle3A) * size.width * 0.5f),
        y = size.height * 0.5f + (sin(angle3B) * size.height * 0.5f)
    )
    val radius3 = size.width * (0.9f + 0.2f * sin(angle3B))

    val fogColor = Color.White.copy(alpha = 0.15f)

    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(fogColor, Color.Transparent),
            center = center1,
            radius = radius1
        )
    )
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(fogColor, Color.Transparent),
            center = center2,
            radius = radius2
        )
    )
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(fogColor, Color.Transparent),
            center = center3,
            radius = radius3
        )
    )
}
