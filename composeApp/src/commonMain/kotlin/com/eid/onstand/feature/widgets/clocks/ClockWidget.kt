package com.eid.onstand.feature.widgets.clocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.data.date.getDayOfWeek
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
fun ClockWidget(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime,
    showSeconds: Boolean = true,
    fontFamily: FontFamily = FontFamily.Default,
) {
    val timePattern = if (showSeconds) "HH:mm:ss" else "HH:mm"
    val currentTimeString =
        currentTime.format(LocalDateTime.Format { byUnicodePattern(timePattern) })

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .height(200.dp).width(550.dp)
                .padding(horizontal = 48.dp, vertical = 24.dp)
                .clip(RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row {
                currentTimeString.forEach { char ->
                    AnimatedContent(targetState = char, transitionSpec = {
                        slideInVertically { it } togetherWith slideOutVertically { -it }
                    }) {
                        Text(
                            text = it.toString(),
                            color = Color.White,
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(48.dp)
                        )
                    }
                }
            }
        }
    }
}


