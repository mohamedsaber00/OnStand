package com.eid.onstand.feature.widgets.clocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

/**
 * A composable function that displays a stylish clock widget.
 * It uses kotlinx-datetime for all date and time operations.
 */
@OptIn(
    FormatStringsInDatetimeFormats::class, ExperimentalAnimationApi::class
)
@Composable
fun BasicClockWidget(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime,
    showSeconds: Boolean = true,
    fontFamily: FontFamily = FontFamily.Default,
    textColor: Color = Color.White,
    isPreview: Boolean = false
) {
    val timePattern = if (showSeconds) "HH:mm:ss" else "HH:mm"
    val currentTimeString =
        currentTime.format(LocalDateTime.Format { byUnicodePattern(timePattern) })

    BoxWithConstraints(modifier = modifier) {
        val scaleFactor = if (isPreview) 0.6f else 1f
        val numChars = if (showSeconds) 8 else 5
        val digitWidth = maxWidth / (numChars.toFloat() * 1.2f) * scaleFactor
        val colonWidth = digitWidth * 0.6f

        // Calculate font size based on both width and height constraints
        val widthBasedFontSize = (digitWidth.value * 1.4f).sp
        val heightBasedFontSize = (maxHeight.value * 0.8f * scaleFactor).sp
        val fontSize = minOf(widthBasedFontSize.value, heightBasedFontSize.value).sp

        Row(
            modifier = Modifier
                .padding(vertical = maxHeight * 0.05f * scaleFactor)
                .clip(RoundedCornerShape(maxWidth * 0.02f * scaleFactor)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            currentTimeString.forEach { char ->
                val isColon = char == ':'
                val width = if (isColon) colonWidth else digitWidth

                AnimatedContent(
                    targetState = char,
                    transitionSpec = {
                        slideInVertically { it } togetherWith slideOutVertically { -it }
                    },
                    label = "DigitAnimation"
                ) { targetChar ->
                    Text(
                        text = targetChar.toString(),
                        color = textColor,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Medium,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(width)
                    )
                }
            }
        }
    }
}


