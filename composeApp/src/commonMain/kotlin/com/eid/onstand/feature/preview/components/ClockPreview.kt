package com.eid.onstand.feature.preview.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.*
import kotlinx.coroutines.delay
import kotlinx.datetime.*
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ClockPreview(
    modifier: Modifier = Modifier,
    backgroundOption: BackgroundOption? = null,
    clockStyle: ClockStyle? = null,
    fontColorOption: FontColorOption? = null,
    layoutOption: LayoutOption? = null
) {
    var currentTime by remember { mutableStateOf(Clock.System.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Clock.System.now()
            delay(1000)
        }
    }

    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = currentTime.toLocalDateTime(timeZone)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(getBackgroundBrush(backgroundOption))
    ) {
        if (clockStyle?.isDigital == true) {
            DigitalClock(
                localDateTime = localDateTime,
                clockStyle = clockStyle,
                fontColorOption = fontColorOption,
                layoutOption = layoutOption,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            AnalogClock(
                localDateTime = localDateTime,
                clockStyle = clockStyle,
                fontColorOption = fontColorOption,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun DigitalClock(
    localDateTime: LocalDateTime,
    clockStyle: ClockStyle?,
    fontColorOption: FontColorOption?,
    layoutOption: LayoutOption?,
    modifier: Modifier = Modifier
) {
    val hour = if (clockStyle?.timeFormat == TimeFormat.TWELVE_HOUR) {
        val hour12 = localDateTime.hour % 12
        if (hour12 == 0) 12 else hour12
    } else {
        localDateTime.hour
    }

    val timeString =
        "${hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"

    val secondsString = if (clockStyle?.showSeconds == true) {
        ":${localDateTime.second.toString().padStart(2, '0')}"
    } else ""

    val dateString = if (clockStyle?.showDate == true) {
        "${localDateTime.dayOfWeek.name.take(3)}, ${localDateTime.month.name.take(3)} ${localDateTime.dayOfMonth}"
    } else ""

    val previousMinutesString = if (layoutOption?.showPreviousMinutes == true) {
        val prevMinute = (localDateTime.minute - 1).let { if (it < 0) 59 else it }
        prevMinute.toString().padStart(2, '0')
    } else ""

    val futureMinutesString = if (layoutOption?.showPreviousMinutes == true) {
        ((localDateTime.minute + 1) % 60).toString().padStart(2, '0')
    } else ""

    Column(
        horizontalAlignment = when (layoutOption?.alignment) {
            TimeAlignment.LEFT -> Alignment.Start
            TimeAlignment.RIGHT -> Alignment.End
            else -> Alignment.CenterHorizontally
        },
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        // Previous minutes (faded)
        if (layoutOption?.showPreviousMinutes == true) {
            Text(
                text = previousMinutesString,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = (fontColorOption?.primaryColor ?: Color.White).copy(alpha = 0.3f),
                textAlign = TextAlign.Center
            )
        }

        // Current time
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = timeString,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = fontColorOption?.primaryColor ?: Color.White,
                textAlign = TextAlign.Center
            )

            if (clockStyle?.showSeconds == true) {
                Text(
                    text = secondsString,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    color = fontColorOption?.secondaryColor ?: Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Date
        if (clockStyle?.showDate == true) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dateString,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = fontColorOption?.secondaryColor ?: Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }

        // Future minutes (faded)
        if (layoutOption?.showPreviousMinutes == true) {
            Text(
                text = futureMinutesString,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = (fontColorOption?.primaryColor ?: Color.White).copy(alpha = 0.3f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AnalogClock(
    localDateTime: LocalDateTime,
    clockStyle: ClockStyle?,
    fontColorOption: FontColorOption?,
    modifier: Modifier = Modifier
) {
    val primaryColor = fontColorOption?.primaryColor ?: Color.White
    val secondaryColor = fontColorOption?.secondaryColor ?: Color.White.copy(alpha = 0.8f)

    Canvas(
        modifier = modifier.size(120.dp)
    ) {
        val center = center
        val radius = size.minDimension / 2 * 0.8f

        // Draw clock face
        drawCircle(
            color = secondaryColor,
            radius = radius,
            center = center,
            style = Stroke(width = 4.dp.toPx())
        )

        // Draw hour markers
        for (i in 0..11) {
            val angle = (i * 30 - 90) * kotlin.math.PI / 180
            val startX = center.x + cos(angle).toFloat() * radius * 0.9f
            val startY = center.y + sin(angle).toFloat() * radius * 0.9f
            val endX = center.x + cos(angle).toFloat() * radius * 0.8f
            val endY = center.y + sin(angle).toFloat() * radius * 0.8f

            drawLine(
                color = secondaryColor,
                start = androidx.compose.ui.geometry.Offset(startX, startY),
                end = androidx.compose.ui.geometry.Offset(endX, endY),
                strokeWidth = 3.dp.toPx()
            )
        }

        // Draw hands
        val hourAngle =
            ((localDateTime.hour % 12) * 30 + localDateTime.minute * 0.5f - 90) * kotlin.math.PI / 180
        val minuteAngle = (localDateTime.minute * 6 - 90) * kotlin.math.PI / 180

        // Hour hand
        drawLine(
            color = primaryColor,
            start = center,
            end = androidx.compose.ui.geometry.Offset(
                center.x + cos(hourAngle).toFloat() * radius * 0.5f,
                center.y + sin(hourAngle).toFloat() * radius * 0.5f
            ),
            strokeWidth = 6.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Minute hand
        drawLine(
            color = primaryColor,
            start = center,
            end = androidx.compose.ui.geometry.Offset(
                center.x + cos(minuteAngle).toFloat() * radius * 0.7f,
                center.y + sin(minuteAngle).toFloat() * radius * 0.7f
            ),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Center dot
        drawCircle(
            color = primaryColor,
            radius = 6.dp.toPx(),
            center = center
        )
    }
}

@Composable
private fun getBackgroundBrush(backgroundOption: BackgroundOption?): Brush {
    return when (backgroundOption?.type) {
        BackgroundType.GRADIENT -> {
            if (backgroundOption.gradientColors.isNotEmpty()) {
                Brush.linearGradient(backgroundOption.gradientColors)
            } else {
                Brush.linearGradient(
                    listOf(
                        Color(0xFF4A90E2),
                        Color(0xFF7B68EE)
                    )
                )
            }
        }

        BackgroundType.SOLID_COLOR -> {
            Brush.linearGradient(
                listOf(
                    backgroundOption.previewColor ?: Color.Black,
                    backgroundOption.previewColor ?: Color.Black
                )
            )
        }

        BackgroundType.FOG -> {
            Brush.radialGradient(
                listOf(
                    Color(0xFFE6E6FA),
                    Color(0xFFD3D3D3),
                    Color(0xFFA9A9A9)
                )
            )
        }

        BackgroundType.ABSTRACT -> {
            Brush.linearGradient(
                listOf(
                    Color(0xFFF0F0F0),
                    Color(0xFFE0E0E0)
                )
            )
        }

        BackgroundType.LIVE -> {
            Brush.linearGradient(
                listOf(
                    Color(0xFF87CEEB),
                    Color(0xFF4682B4)
                )
            )
        }

        else -> {
            Brush.linearGradient(
                listOf(
                    Color(0xFF2C2C2C),
                    Color(0xFF1A1A1A)
                )
            )
        }
    }
}