package com.eid.onstand.feature.widgets.clocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.utils.toComposeFontFamily
import dev.chrisbanes.haze.HazeState
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import com.eid.onstand.core.models.FontFamily as AppFontFamily

/**
 * A composable function for a very basic clock widget.
 * It uses kotlinx-datetime for all date and time operations.
 */
@OptIn(
    FormatStringsInDatetimeFormats::class, ExperimentalAnimationApi::class
)
@Composable
internal fun BasicClockWidget(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime,
    showSeconds: Boolean = false,
    fontFamily: AppFontFamily = AppFontFamily.ROBOTO,
    textColor: Color = Color.White,
    isPreview: Boolean = false,
    hazeState: HazeState? = null,
) {
    val timePattern = "HH:mm"
    val currentTimeString =
        currentTime.format(LocalDateTime.Format { byUnicodePattern(timePattern) })

    // Format date and day using manual formatting to avoid locale dependency
    val dayNames =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val monthNames =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    val dayString = dayNames[currentTime.dayOfWeek.ordinal]
    val dateString =
        "${monthNames[currentTime.month.ordinal]} ${currentTime.day}, ${currentTime.year}"



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Time display with improved animations
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            currentTimeString.forEach { char ->
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
                        fontSize = if (isPreview) 24.sp else 45.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = fontFamily.toComposeFontFamily(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Day and date display with better spacing
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = dayString,
            color = textColor.copy(alpha = 0.9f),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily.toComposeFontFamily(),
            textAlign = TextAlign.Center
        )
        Text(
            text = dateString,
            color = textColor.copy(alpha = 0.8f),
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamily.toComposeFontFamily(),
            textAlign = TextAlign.Center
        )
    }
}
