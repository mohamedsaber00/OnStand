package com.eid.onstand.feature.widgets.clocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClockWidget(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime,
    clockColor: Color = Color.White,
    handsColor: Color = Color.White,
    numbersColor: Color = Color.White.copy(alpha = 0.8f)
) {
    val timeTriple = Triple(currentTime.hour % 12, currentTime.minute, currentTime.second)

    Box(
        modifier = modifier
            .size(300.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2 - 20.dp.toPx()

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
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
            )

            // Draw hour markers
            repeat(12) { hour ->
                val angle = (hour * 30f - 90f) * (kotlin.math.PI / 180f)
                val isMainHour = hour % 3 == 0
                val markerLength = if (isMainHour) 40.dp.toPx() else 20.dp.toPx()
                val markerWidth = if (isMainHour) 3.dp.toPx() else 2.dp.toPx()

                val startRadius = radius - markerLength
                val endRadius = radius - 5.dp.toPx()

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
                    val markerLength = 10.dp.toPx()
                    val startRadius = radius - markerLength
                    val endRadius = radius - 5.dp.toPx()

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
                        strokeWidth = 1.dp.toPx(),
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
                radius = 8.dp.toPx(),
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
            strokeWidth = 6.dp.toPx(),
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
            strokeWidth = 4.dp.toPx(),
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
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}