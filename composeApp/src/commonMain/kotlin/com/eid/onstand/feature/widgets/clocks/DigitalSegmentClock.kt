package com.eid.onstand.feature.widgets.clocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
    inactiveColor: Color = Color.White.copy(alpha = 0.1f),
    isPreview: Boolean = false
) {
    val hour = currentTime.format(LocalDateTime.Format { byUnicodePattern("HH") })
    val minute = currentTime.format(LocalDateTime.Format { byUnicodePattern("mm") })
    val seconds = currentTime.format(LocalDateTime.Format { byUnicodePattern("ss") })
    val timeString = if (showSeconds) "$hour$minute$seconds" else "$hour$minute"

    BoxWithConstraints(modifier = modifier) {
        val numDigits = if (showSeconds) 6 else 4
        val numColons = if (showSeconds) 2 else 1
        val totalParts = numDigits + numColons

        val scaleFactor = if (isPreview) 0.6f else 1f
        val availableWidth = maxWidth.value * 0.9f * scaleFactor
        val digitWidth = (availableWidth / (numDigits + numColons * 0.3f) * 0.8f).dp
        val colonWidth = (digitWidth.value * 0.3f).dp

        val widthBasedHeight = (digitWidth.value * 1.8f).dp
        val heightBasedHeight = (maxHeight.value * 0.6f * scaleFactor).dp
        val digitHeight = minOf(widthBasedHeight.value, heightBasedHeight.value).dp

        val padding = (maxWidth.value * 0.03f * scaleFactor).dp
        val cornerRadius = (padding.value * 1.5f).dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .clip(RoundedCornerShape(cornerRadius)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding * 0.5f)
            ) {
                val activeBrush = Brush.verticalGradient(
                    colors = listOf(
                        activeColor,
                        activeColor.copy(alpha = 0.9f)
                    )
                )

                SegmentedDigit(
                    digit = timeString[0],
                    width = digitWidth,
                    height = digitHeight,
                    activeBrush = activeBrush,
                    inactiveColor = inactiveColor
                )
                SegmentedDigit(
                    digit = timeString[1],
                    width = digitWidth,
                    height = digitHeight,
                    activeBrush = activeBrush,
                    inactiveColor = inactiveColor
                )
                ColonSeparator(
                    width = colonWidth,
                    height = digitHeight,
                    color = activeColor
                )
                SegmentedDigit(
                    digit = timeString[2],
                    width = digitWidth,
                    height = digitHeight,
                    activeBrush = activeBrush,
                    inactiveColor = inactiveColor
                )
                SegmentedDigit(
                    digit = timeString[3],
                    width = digitWidth,
                    height = digitHeight,
                    activeBrush = activeBrush,
                    inactiveColor = inactiveColor
                )

                if (showSeconds) {
                    ColonSeparator(
                        width = colonWidth,
                        height = digitHeight,
                        color = activeColor
                    )
                    SegmentedDigit(
                        digit = timeString[4],
                        width = digitWidth,
                        height = digitHeight,
                        activeBrush = activeBrush,
                        inactiveColor = inactiveColor
                    )
                    SegmentedDigit(
                        digit = timeString[5],
                        width = digitWidth,
                        height = digitHeight,
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
    width: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp,
    activeBrush: Brush,
    inactiveColor: Color
) {
    val segmentState = segmentPatterns[digit] ?: List(7) { false }
    Canvas(modifier = Modifier.size(width, height)) {
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
    width: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp,
    color: Color
) {
    Column(
        modifier = Modifier.size(width, height),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val dotSize = (width.value * 0.8f).dp
        val spacerHeight = (height.value * 0.2f).dp
        Canvas(modifier = Modifier.size(dotSize)) {
            drawCircle(color = color, radius = size.minDimension / 2)
        }
        Spacer(modifier = Modifier.height(spacerHeight))
        Canvas(modifier = Modifier.size(dotSize)) {
            drawCircle(color = color, radius = size.minDimension / 2)
        }
    }
}
