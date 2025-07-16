package com.eid.onstand.feature.widgets.clocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun FlipClockWidget(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime,
    cardColor: Color = Color.Black.copy(alpha = 0.8f),
    textColor: Color = Color.White,
    fontFamily: FontFamily = FontFamily.Monospace
) {
    val hour = currentTime.format(LocalDateTime.Format { byUnicodePattern("HH") })
    val minute = currentTime.format(LocalDateTime.Format { byUnicodePattern("mm") })
    val second = currentTime.format(LocalDateTime.Format { byUnicodePattern("ss") })
    val dayOfWeek = getDayOfWeek(currentTime)
    val date = currentTime.format(LocalDateTime.Format { byUnicodePattern("dd/MM") })

    val timeTriple = Triple(hour, minute, second)
    val dateString = "$dayOfWeek, $date"

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main time display
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlipCard(
                    value = timeTriple.first,
                    cardColor = cardColor,
                    textColor = textColor,
                    fontFamily = fontFamily
                )

                Text(
                    text = ":",
                    color = textColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                )

                FlipCard(
                    value = timeTriple.second,
                    cardColor = cardColor,
                    textColor = textColor,
                    fontFamily = fontFamily
                )

                Text(
                    text = ":",
                    color = textColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                )

                FlipCard(
                    value = timeTriple.third,
                    cardColor = cardColor,
                    textColor = textColor,
                    fontFamily = fontFamily,
                    isSeconds = true
                )
            }

            // Date display
            Text(
                text = dateString,
                color = textColor.copy(alpha = 0.8f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily
            )
        }
    }
}

@Composable
private fun FlipCard(
    value: String,
    cardColor: Color,
    textColor: Color,
    fontFamily: FontFamily,
    isSeconds: Boolean = false
) {
    val cardSize = if (isSeconds) 60.dp else 80.dp
    val fontSize = if (isSeconds) 24.sp else 32.sp

    AnimatedContent(
        targetState = value,
        transitionSpec = {
            (slideInVertically(
                animationSpec = tween(durationMillis = 300),
                initialOffsetY = { it }
            ) + fadeIn(animationSpec = tween(durationMillis = 300))) togetherWith
                    (slideOutVertically(
                        animationSpec = tween(durationMillis = 300),
                        targetOffsetY = { -it }
                    ) + fadeOut(animationSpec = tween(durationMillis = 300)))
        },
        label = "FlipCard"
    ) { targetValue ->
        Box(
            modifier = Modifier
                .size(width = cardSize, height = cardSize)
                .background(
                    color = cardColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = targetValue,
                color = textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}