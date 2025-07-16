package com.eid.onstand.feature.widgets.clocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

private val segmentPatterns = mapOf(
    '0' to listOf(true, true, true, true, true, true, false),
    '1' to listOf(false, true, true, false, false, false, false),
    '2' to listOf(true, true, false, true, true, false, true),
    '3' to listOf(true, true, true, true, false, false, true),
    '4' to listOf(false, true, true, false, false, true, true),
    '5' to listOf(true, false, true, true, false, true, true),
    '6' to listOf(true, false, true, true, true, true, true),
    '7' to listOf(true, true, true, false, false, false, false),
    '8' to listOf(true, true, true, true, true, true, true),
    '9' to listOf(true, true, true, true, false, true, true),
)

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun DigitalSegmentClock(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime,
    showSeconds: Boolean = true,
    activeColor: Color = Color.White.copy(alpha = 0.9f),
    inactiveColor: Color = Color.White.copy(alpha = 0.1f)
) {
    val hour = currentTime.format(LocalDateTime.Format { byUnicodePattern("HH") })
    val minute = currentTime.format(LocalDateTime.Format { byUnicodePattern("mm") })
    val seconds = currentTime.format(LocalDateTime.Format { byUnicodePattern("ss") })
    val timeString = if (showSeconds) "$hour$minute$seconds" else "$hour$minute"

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .padding(horizontal = 32.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val activeBrush = Brush.verticalGradient(
                    colors = listOf(
                        activeColor,
                        activeColor.copy(alpha = 0.9f)
                    )
                )

                SegmentedDigit(
                    digit = timeString[0],
                    activeBrush = activeBrush,
                    inactiveColor = inactiveColor
                )
                SegmentedDigit(
                    digit = timeString[1],
                    activeBrush = activeBrush,
                    inactiveColor = inactiveColor
                )
                ColonSeparator(color = activeColor)
                SegmentedDigit(
                    digit = timeString[2],
                    activeBrush = activeBrush,
                    inactiveColor = inactiveColor
                )
                SegmentedDigit(
                    digit = timeString[3],
                    activeBrush = activeBrush,
                    inactiveColor = inactiveColor
                )

                if (showSeconds) {
                    ColonSeparator(color = activeColor)
                    SegmentedDigit(
                        digit = timeString[4],
                        activeBrush = activeBrush,
                        inactiveColor = inactiveColor
                    )
                    SegmentedDigit(
                        digit = timeString[5],
                        activeBrush = activeBrush,
                        inactiveColor = inactiveColor
                    )
                }
            }
        }
    }
}

@Composable
private fun SegmentedDigit(
    digit: Char,
    modifier: Modifier = Modifier,
    activeBrush: Brush,
    inactiveColor: Color
) {
    val segmentState = segmentPatterns[digit] ?: List(7) { false }
    Canvas(modifier = modifier.size(width = 60.dp, height = 120.dp)) {
        val segmentWidth = size.width / 5f
        val segmentHeight = segmentWidth
        val cornerRadius = CornerRadius(segmentHeight / 2, segmentHeight / 2)

        val segments = listOf(
            // Top
            Triple(
                Offset(segmentWidth, 0f),
                Size(segmentWidth * 3, segmentHeight),
                segmentState[0]
            ),
            // Top-right
            Triple(
                Offset(segmentWidth * 4, segmentHeight),
                Size(segmentWidth, segmentHeight * 3),
                segmentState[1]
            ),
            // Bottom-right
            Triple(
                Offset(segmentWidth * 4, segmentHeight * 5),
                Size(segmentWidth, segmentHeight * 3),
                segmentState[2]
            ),
            // Bottom
            Triple(
                Offset(segmentWidth, segmentHeight * 8),
                Size(segmentWidth * 3, segmentHeight),
                segmentState[3]
            ),
            // Bottom-left
            Triple(
                Offset(0f, segmentHeight * 5),
                Size(segmentWidth, segmentHeight * 3),
                segmentState[4]
            ),
            // Top-left
            Triple(
                Offset(0f, segmentHeight),
                Size(segmentWidth, segmentHeight * 3),
                segmentState[5]
            ),
            // Middle
            Triple(
                Offset(segmentWidth, segmentHeight * 4),
                Size(segmentWidth * 3, segmentHeight),
                segmentState[6]
            ),
        )

        segments.forEach { (position, size, isActive) ->
            drawRoundRect(
                brush = if (isActive) activeBrush else SolidColor(inactiveColor),
                topLeft = position,
                size = size,
                cornerRadius = cornerRadius
            )
        }
    }
}

@Composable
private fun ColonSeparator(
    modifier: Modifier = Modifier,
    color: Color
) {
    Column(
        modifier = modifier.height(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val dotSize = 12.dp
        Canvas(modifier = Modifier.size(dotSize)) {
            drawCircle(color = color, radius = size.minDimension / 2)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Canvas(modifier = Modifier.size(dotSize)) {
            drawCircle(color = color, radius = size.minDimension / 2)
        }
    }
}
