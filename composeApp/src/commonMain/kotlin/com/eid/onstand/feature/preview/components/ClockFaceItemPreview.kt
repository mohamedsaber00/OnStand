package com.eid.onstand.feature.preview.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily as ComposeFontFamily
import androidx.compose.ui.unit.dp
import com.eid.onstand.core.models.ClockType
import com.eid.onstand.core.models.FontFamily
import com.eid.onstand.core.models.ClockColor
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
    fontFamily: FontFamily = FontFamily.ROBOTO,
    clockColor: ClockColor = ClockColor("White", Color.White),
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
                    activeColor = clockColor.color,
                    inactiveColor = clockColor.color.copy(alpha = 0.1f),
                    isPreview = true
                )
            }

            is ClockType.MorphFlip -> {
                MorphFlipClockWidget(
                    currentTime = currentTime,
                    cardColor = Color(0xFFFFA77A).copy(alpha = 0.85f),
                    textColor = clockColor.color,
                    fontFamily = fontFamily,
                    isPreview = true
                )
            }

            is ClockType.Analog -> {
                AnalogClockWidget(
                    currentTime = currentTime,
                    clockColor = clockColor.color,
                    handsColor = clockColor.color,
                    numbersColor = clockColor.color.copy(alpha = 0.8f),
                    modifier = Modifier.size(60.dp)
                )
            }

            is ClockType.Digital -> {
                BasicClockWidget(
                    currentTime = currentTime,
                    showSeconds = clockType.showSeconds,
                    fontFamily = fontFamily,
                    textColor = clockColor.color,
                    isPreview = true,
                )
            }
        }
    }
}