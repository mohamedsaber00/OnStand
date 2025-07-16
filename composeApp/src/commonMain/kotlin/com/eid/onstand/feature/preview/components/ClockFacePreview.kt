package com.eid.onstand.feature.preview.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.ClockStyle
import com.eid.onstand.core.models.FontColorOption
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ClockFacePreview(
    clockStyle: ClockStyle,
    fontColorOption: FontColorOption,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    var currentTime by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            delay(1000)
        }
    }

    Box(
        modifier = modifier.size(120.dp, 80.dp),
        contentAlignment = Alignment.Center
    ) {
        when (clockStyle.id) {
            "digital_segments" -> {
                DigitalSegmentsPreview(
                    currentTime = currentTime,
                    showSeconds = clockStyle.showSeconds,
                    activeColor = fontColorOption.primaryColor,
                    inactiveColor = fontColorOption.primaryColor.copy(alpha = 0.1f)
                )
            }
            "flip_clock" -> {
                FlipClockPreview(
                    currentTime = currentTime,
                    textColor = fontColorOption.primaryColor,
                    fontFamily = getFontFamily(clockStyle.fontFamily)
                )
            }
            "analog_classic" -> {
                AnalogClockPreview(
                    currentTime = currentTime,
                    clockColor = fontColorOption.primaryColor,
                    handsColor = fontColorOption.primaryColor,
                    numbersColor = fontColorOption.primaryColor.copy(alpha = 0.8f)
                )
            }
            else -> {
                // For other digital clocks
                DigitalClockPreview(
                    currentTime = currentTime,
                    showSeconds = clockStyle.showSeconds,
                    textColor = fontColorOption.primaryColor,
                    fontFamily = getFontFamily(clockStyle.fontFamily)
                )
            }
        }
    }
}

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
private fun DigitalClockPreview(
    currentTime: LocalDateTime,
    showSeconds: Boolean,
    textColor: Color,
    fontFamily: FontFamily
) {
    val timeString = if (showSeconds) {
        currentTime.format(LocalDateTime.Format { byUnicodePattern("HH:mm:ss") })
    } else {
        currentTime.format(LocalDateTime.Format { byUnicodePattern("HH:mm") })
    }

    Text(
        text = timeString,
        color = textColor,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamily,
        textAlign = TextAlign.Center
    )
}

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
private fun FlipClockPreview(
    currentTime: LocalDateTime,
    textColor: Color,
    fontFamily: FontFamily
) {
    val timeString = currentTime.format(LocalDateTime.Format { byUnicodePattern("HH:mm") })

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        timeString.split(":").forEachIndexed { index, part ->
            if (index > 0) {
                Text(
                    text = ":",
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                )
            }

            Box(
                modifier = Modifier
                    .size(20.dp, 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Simple rectangle background to simulate flip card
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRoundRect(
                        color = Color.Black.copy(alpha = 0.3f),
                        size = size,
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx())
                    )
                }

                Text(
                    text = part,
                    color = textColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun DigitalSegmentsPreview(
    currentTime: LocalDateTime,
    showSeconds: Boolean,
    activeColor: Color,
    inactiveColor: Color
) {
    val hour = currentTime.hour.toString().padStart(2, '0')
    val minute = currentTime.minute.toString().padStart(2, '0')
    val timeString = if (showSeconds) {
        val seconds = currentTime.second.toString().padStart(2, '0')
        "$hour$minute$seconds"
    } else {
        "$hour$minute"
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Draw simplified 7-segment displays
        timeString.forEachIndexed { index, char ->
            if (index == 2 || (showSeconds && index == 4)) {
                // Draw colon separator
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Canvas(modifier = Modifier.size(2.dp)) {
                        drawCircle(color = activeColor)
                    }
                    Canvas(modifier = Modifier.size(2.dp)) {
                        drawCircle(color = activeColor)
                    }
                }
            }

            // Draw simplified digit
            MiniSegmentDigit(
                digit = char,
                activeColor = activeColor,
                inactiveColor = inactiveColor
            )
        }
    }
}

@Composable
private fun MiniSegmentDigit(
    digit: Char,
    activeColor: Color,
    inactiveColor: Color
) {
    val segmentPattern = when (digit) {
        '0' -> listOf(true, true, true, true, true, true, false)
        '1' -> listOf(false, true, true, false, false, false, false)
        '2' -> listOf(true, true, false, true, true, false, true)
        '3' -> listOf(true, true, true, true, false, false, true)
        '4' -> listOf(false, true, true, false, false, true, true)
        '5' -> listOf(true, false, true, true, false, true, true)
        '6' -> listOf(true, false, true, true, true, true, true)
        '7' -> listOf(true, true, true, false, false, false, false)
        '8' -> listOf(true, true, true, true, true, true, true)
        '9' -> listOf(true, true, true, true, false, true, true)
        else -> listOf(false, false, false, false, false, false, false)
    }

    Canvas(modifier = Modifier.size(8.dp, 12.dp)) {
        val segmentWidth = size.width / 4f
        val segmentHeight = segmentWidth / 2f

        // Draw simplified segments
        val segments = listOf(
            Pair(Offset(segmentWidth, 0f), segmentPattern[0]), // Top
            Pair(Offset(segmentWidth * 3, segmentHeight), segmentPattern[1]), // Top-right
            Pair(Offset(segmentWidth * 3, segmentHeight * 3), segmentPattern[2]), // Bottom-right
            Pair(Offset(segmentWidth, segmentHeight * 4), segmentPattern[3]), // Bottom
            Pair(Offset(0f, segmentHeight * 3), segmentPattern[4]), // Bottom-left
            Pair(Offset(0f, segmentHeight), segmentPattern[5]), // Top-left
            Pair(Offset(segmentWidth, segmentHeight * 2), segmentPattern[6]), // Middle
        )

        segments.forEach { (position, isActive) ->
            drawCircle(
                color = if (isActive) activeColor else inactiveColor,
                radius = segmentWidth / 4,
                center = position
            )
        }
    }
}

@Composable
private fun AnalogClockPreview(
    currentTime: LocalDateTime,
    clockColor: Color,
    handsColor: Color,
    numbersColor: Color
) {
    Canvas(modifier = Modifier.size(60.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2 - 4.dp.toPx()

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
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
        )

        // Draw hour markers
        repeat(12) { hour ->
            val angle = (hour * 30f - 90f) * (kotlin.math.PI / 180f)
            val markerLength = if (hour % 3 == 0) 8.dp.toPx() else 4.dp.toPx()
            val markerWidth = if (hour % 3 == 0) 2.dp.toPx() else 1.dp.toPx()
            val startRadius = radius - markerLength
            val endRadius = radius - 2.dp.toPx()

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

        // Draw hands
        drawHands(
            center = center,
            radius = radius,
            hours = currentTime.hour % 12,
            minutes = currentTime.minute,
            seconds = currentTime.second,
            handsColor = handsColor
        )

        // Draw center dot
        drawCircle(
            color = handsColor,
            radius = 2.dp.toPx(),
            center = center
        )
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
            strokeWidth = 2.dp.toPx(),
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
            strokeWidth = 1.5.dp.toPx(),
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
            strokeWidth = 1.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

private fun getFontFamily(fontFamilyName: String): FontFamily {
    return when (fontFamilyName.lowercase()) {
        "roboto" -> FontFamily.Default
        "serif" -> FontFamily.Serif
        "helvetica" -> FontFamily.SansSerif
        "monospace" -> FontFamily.Monospace
        else -> FontFamily.Default
    }
}