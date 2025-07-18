package com.eid.onstand.feature.preview.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.eid.onstand.core.models.ClockType
import com.eid.onstand.core.models.FontColorOption
import com.eid.onstand.feature.widgets.clocks.*
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun ClockFaceItemPreview(
    clockType: ClockType,
    fontColorOption: FontColorOption,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    var currentTime by remember {
        mutableStateOf(
            kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime =
                kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            delay(1000)
        }
    }

    Box(
        modifier = modifier.size(120.dp, 80.dp),
        contentAlignment = Alignment.Center
    ) {
        when (clockType) {
            is ClockType.DigitalSegments -> {
                DigitalSegmentClock(
                    currentTime = currentTime,
                    showSeconds = clockType.showSeconds,
                    activeColor = fontColorOption.primaryColor,
                    inactiveColor = fontColorOption.primaryColor.copy(alpha = 0.1f),
                    modifier = Modifier.size(100.dp, 60.dp)
                )
            }

            is ClockType.Flip -> {
                FlipClockWidget(
                    currentTime = currentTime,
                    cardColor = Color.Black.copy(alpha = 0.6f),
                    textColor = fontColorOption.primaryColor,
                    fontFamily = getFontFamily(clockType.fontFamily),
                    modifier = Modifier.size(100.dp, 60.dp)
                )
            }

            is ClockType.MorphFlip -> {
                MorphFlipClockWidget(
                    currentTime = currentTime,
                    cardColor = Color(0xFFFFA77A).copy(alpha = 0.85f),
                    textColor = fontColorOption.primaryColor,
                    fontFamily = getFontFamily(clockType.fontFamily),
                    modifier = Modifier.size(100.dp, 60.dp)
                )
            }

            is ClockType.Analog -> {
                AnalogClockWidget(
                    currentTime = currentTime,
                    clockColor = fontColorOption.primaryColor,
                    handsColor = fontColorOption.primaryColor,
                    numbersColor = fontColorOption.primaryColor.copy(alpha = 0.8f),
                    modifier = Modifier.size(60.dp)
                )
            }

            is ClockType.Digital -> {
                BasicClockWidget(
                    currentTime = currentTime,
                    showSeconds = clockType.showSeconds,
                    fontFamily = getFontFamily(clockType.fontFamily),
                    textColor = fontColorOption.primaryColor,
                    modifier = Modifier.size(100.dp, 40.dp)
                )
            }
        }
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