package com.eid.onstand.feature.widgets.clocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.eid.onstand.core.ui.utils.ClockSizingUtils
import dev.chrisbanes.haze.HazeState
import kotlinx.datetime.LocalDateTime
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun AnalogClockWidget(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime,
    clockColor: Color = Color.White,
    handsColor: Color = Color.White,
    numbersColor: Color = Color.White.copy(alpha = 0.8f),
    isPreview: Boolean = false,
    hazeState: HazeState? = null,
) {
    val timeTriple = Triple(currentTime.hour % 12, currentTime.minute, currentTime.second)


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val padding = ClockSizingUtils.calculateAdaptivePadding(
            maxWidth, maxHeight, isPreview
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2 - padding.toPx()

            // Draw clock face
            drawCircle(
                color = clockColor.copy(alpha = 0.1f),
                radius = radius,
                center = center
            )

            // Draw clock border
            drawCircle(
                color = clockColor.copy(alpha = 0.3f),
                radius = radius,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = (size.minDimension * 0.01f))
            )

            // Draw hour markers
            repeat(12) { hour ->
                val angle = (hour * 30f - 90f) * (kotlin.math.PI / 180f)
                val isMainHour = hour % 3 == 0
                val markerLength = if (isMainHour) radius * 0.15f else radius * 0.08f
                val markerWidth = if (isMainHour) radius * 0.01f else radius * 0.005f

                val startRadius = radius - markerLength
                val endRadius = radius - (radius * 0.02f)

                drawLine(
                    color = numbersColor,
                    start = Offset(
                        center.x + cos(angle).toFloat() * startRadius,
                        center.y + sin(angle).toFloat() * startRadius
                    ),
                    end = Offset(
                        center.x + cos(angle).toFloat() * endRadius,
                        center.y + sin(angle).toFloat() * endRadius
                    ),
                    strokeWidth = markerWidth,
                    cap = StrokeCap.Round
                )
            }

            // Draw minute markers
            repeat(60) { minute ->
                if (minute % 5 != 0) {
                    val angle = (minute * 6f - 90f) * (kotlin.math.PI / 180f)
                    val markerLength = radius * 0.04f
                    val startRadius = radius - markerLength
                    val endRadius = radius - (radius * 0.02f)

                    drawLine(
                        color = numbersColor.copy(alpha = 0.3f),
                        start = Offset(
                            center.x + cos(angle).toFloat() * startRadius,
                            center.y + sin(angle).toFloat() * startRadius
                        ),
                        end = Offset(
                            center.x + cos(angle).toFloat() * endRadius,
                            center.y + sin(angle).toFloat() * endRadius
                        ),
                        strokeWidth = (radius * 0.003f).coerceAtLeast(1f),
                        cap = StrokeCap.Round
                    )
                }
            }

            // Draw hands
            drawHands(
                center = center,
                radius = radius,
                hours = timeTriple.first,
                minutes = timeTriple.second,
                seconds = timeTriple.third,
                handsColor = handsColor
            )

            // Draw center dot
            drawCircle(
                color = handsColor,
                radius = radius * 0.03f,
                center = center
            )
        }
    }

}

private fun DrawScope.drawHands(
    center: Offset,
    radius: Float,
    hours: Int,
    minutes: Int,
    seconds: Int,
    handsColor: Color
) {
    // Hour hand
    val hourAngle = (hours * 30f + minutes * 0.5f - 90f)
    rotate(hourAngle, center) {
        drawLine(
            color = handsColor,
            start = center,
            end = Offset(center.x + radius * 0.5f, center.y),
            strokeWidth = radius * 0.02f,
            cap = StrokeCap.Round
        )
    }

    // Minute hand
    val minuteAngle = (minutes * 6f - 90f)
    rotate(minuteAngle, center) {
        drawLine(
            color = handsColor,
            start = center,
            end = Offset(center.x + radius * 0.75f, center.y),
            strokeWidth = radius * 0.015f,
            cap = StrokeCap.Round
        )
    }

    // Second hand
    val secondAngle = (seconds * 6f - 90f)
    rotate(secondAngle, center) {
        drawLine(
            color = Color.Red.copy(alpha = 0.8f),
            start = center,
            end = Offset(center.x + radius * 0.9f, center.y),
            strokeWidth = radius * 0.005f,
            cap = StrokeCap.Round
        )
    }
}