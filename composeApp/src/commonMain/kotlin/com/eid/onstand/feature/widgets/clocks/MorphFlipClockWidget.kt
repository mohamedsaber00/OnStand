 @file:Suppress("MagicNumber")

package com.eid.onstand.feature.widgets.clocks

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import kotlinx.datetime.LocalDateTime
import com.eid.onstand.data.date.getDayOfWeek
import kotlinx.datetime.format
import kotlinx.datetime.format.byUnicodePattern

@Composable
fun MorphFlipClockWidget(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime,
    cardColor: Color = Color(0xFFFFA77A).copy(alpha = 0.85f),
    textColor: Color = Color.Black,
    fontFamily: FontFamily = FontFamily.Default
) {
    val hour = currentTime.format(LocalDateTime.Format { byUnicodePattern("HH") })
    val minute = currentTime.format(LocalDateTime.Format { byUnicodePattern("mm") })
    val dayOfWeek = getDayOfWeek(currentTime) // e.g., "Thu"
    val monthAbbr = currentTime.month.name.take(3) // JAN, FEB...
    val monthDay = "$monthAbbr ${currentTime.dayOfMonth}"
    val amPm = if (currentTime.hour < 12) "AM" else "PM"

    BoxWithConstraints(modifier = modifier) {
        val padding = (maxWidth.value * 0.04f).dp
        val cardWidth = (maxWidth.value * 0.3f).dp
        val cardHeight = (maxHeight.value * 0.55f).dp
        val fontSize = (cardHeight.value * 0.55f).sp
        val sideLabelFont = (cardHeight.value * 0.18f).sp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            // Main display
            Row(
                horizontalArrangement = Arrangement.spacedBy(padding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MorphFlipCard(
                    value = hour,
                    width = cardWidth,
                    height = cardHeight,
                    cardColor = cardColor,
                    textColor = textColor,
                    fontFamily = fontFamily,
                    fontSize = fontSize
                )
                MorphFlipCard(
                    value = minute,
                    width = cardWidth,
                    height = cardHeight,
                    cardColor = cardColor,
                    textColor = textColor,
                    fontFamily = fontFamily,
                    fontSize = fontSize
                )

                // Side labels (AM/PM + date)
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = amPm,
                        color = textColor,
                        fontSize = sideLabelFont,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = dayOfWeek,
                            color = textColor,
                            fontSize = sideLabelFont,
                            fontWeight = FontWeight.Medium,
                            fontFamily = fontFamily
                        )
                        Text(
                            text = monthDay,
                            color = textColor,
                            fontSize = sideLabelFont,
                            fontWeight = FontWeight.Medium,
                            fontFamily = fontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MorphFlipCard(
    value: String,
    width: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp,
    cardColor: Color,
    textColor: Color,
    fontFamily: FontFamily,
    fontSize: androidx.compose.ui.unit.TextUnit
) {
    val cornerRadius = (height.value * 0.15f).dp
    Box(
        modifier = Modifier
            .size(width, height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(cardColor),
        contentAlignment = Alignment.Center
    ) {
        // Horizontal divider line
        Canvas(modifier = Modifier.matchParentSize()) {
            val y = size.height / 2
            drawLine(
                color = textColor.copy(alpha = 0.6f),
                start = androidx.compose.ui.geometry.Offset(0f, y),
                end = androidx.compose.ui.geometry.Offset(size.width, y),
                strokeWidth = size.height * 0.015f
            )
        }
        Crossfade(
            targetState = value,
            animationSpec = tween(durationMillis = 500)
        ) { targetValue ->
            Text(
                text = targetValue,
                color = textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Black,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}
