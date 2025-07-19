package com.eid.onstand.feature.backgrounds.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.eid.onstand.core.theme.GradientConstants
import kotlin.math.PI
import kotlin.math.sin

/**
 * A composable that draws an animated, wavy gradient background.
 * The colors of the gradient can be customized.
 *
 * @param modifier The modifier to be applied to the canvas.
 * @param colors The list of colors to use for the gradient.
 */
@Composable
fun AnimatedBackground(
    modifier: Modifier = Modifier,
    colors: List<Color> = GradientConstants.ANIMATED_BACKGROUND_COLORS
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite color animation")
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f, // Animate a full cycle from 0 to 1
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 20000,
                easing = LinearEasing
            ), // 20-second loop for slower movement
            repeatMode = RepeatMode.Restart // Back to restart for continuous forward motion
        ),
        label = "animation_progress"
    )

    Canvas(modifier = modifier) {
        drawWavyGradientPath(animationProgress = animationProgress, colors = colors)
    }
}

/**
 * Draws the animated wavy gradient path using a Path and a Brush.
 * @param animationProgress The current progress of the animation, from 0.0 to 1.0.
 * @param colors The list of colors for the gradient.
 */
private fun DrawScope.drawWavyGradientPath(animationProgress: Float, colors: List<Color>) {
    val path = Path()
    val waveAmplitude = size.height * 0.15f
    val waveLength = size.width * 1.5f
    val animationTime = animationProgress * 2f * PI.toFloat()

    // Define the starting point of the wave path
    val startX = -size.width * 0.1f // Start off-screen to avoid empty corners
    val startY =
        size.height / 2f + sin((startX / waveLength) * 2f * PI.toFloat() + animationTime) * waveAmplitude
    path.moveTo(startX, startY)

    // Create the segments of the wave path across the screen
    val numSegments = 100
    val segmentWidth = size.width * 1.2f / numSegments
    for (i in 1..numSegments) {
        val x = startX + i * segmentWidth
        val waveAngle = (x / waveLength) * 2f * PI.toFloat() + animationTime
        val y = size.height / 2f + sin(waveAngle) * waveAmplitude
        path.lineTo(x, y)
    }

    // Animate the gradient by shifting its start and end points horizontally
    val gradientWidth = size.width * 2f // Increased width for stretched colors
    val gradientStartOffset =
        -gradientWidth + (animationProgress * gradientWidth * 2f) // Full cycle movement

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(gradientStartOffset, 0f),
        end = Offset(gradientStartOffset + gradientWidth, 0f),
        tileMode = TileMode.Mirror // Mirror mode for seamless transitions
    )

    // Draw the path with a thick stroke using the animated brush
    drawPath(
        path = path,
        brush = brush,
        style = Stroke(width = 300f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}