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
import com.eid.onstand.data.date.getDayOfWeek
import kotlinx.datetime.LocalDateTime
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
    fontFamily: FontFamily = FontFamily.Monospace,
    isPreview: Boolean = false
) {
    val hour = currentTime.format(LocalDateTime.Format { byUnicodePattern("HH") })
    val minute = currentTime.format(LocalDateTime.Format { byUnicodePattern("mm") })
    val second = currentTime.format(LocalDateTime.Format { byUnicodePattern("ss") })
    val dayOfWeek = getDayOfWeek(currentTime)
    val date = currentTime.format(LocalDateTime.Format { byUnicodePattern("dd/MM") })

    val timeTriple = Triple(hour, minute, second)
    val dateString = "$dayOfWeek, $date"

    BoxWithConstraints(modifier = modifier) {
        val scaleFactor = if (isPreview) 0.5f else 1f
        val padding = (maxWidth.value * 0.05f * scaleFactor).dp
        val cornerRadius = padding

        val cardWidth =
            (maxWidth.value * 0.18f * scaleFactor).dp // Adjust factors based on desired proportions
        val cardHeight =
            (maxHeight.value * 0.35f * scaleFactor).dp.coerceAtMost((cardWidth.value * 1.2f).dp)
        val secondsCardWidth = (cardWidth.value * 0.75f).dp
        val secondsCardHeight = (cardHeight.value * 0.75f).dp

        val fontSize = (cardWidth.value * 0.4f).sp
        val secondsFontSize = (secondsCardWidth.value * 0.4f).sp
        val dateFontSize = (maxWidth.value * 0.05f * scaleFactor).sp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .clip(RoundedCornerShape(cornerRadius)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(padding * 0.5f)
            ) {
                // Main time display
                Row(
                    horizontalArrangement = Arrangement.spacedBy(padding * 0.3f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FlipCard(
                        value = timeTriple.first,
                        cardColor = cardColor,
                        textColor = textColor,
                        fontFamily = fontFamily,
                        cardWidth = cardWidth,
                        cardHeight = cardHeight,
                        fontSize = fontSize
                    )

                    Text(
                        text = ":",
                        color = textColor,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )

                    FlipCard(
                        value = timeTriple.second,
                        cardColor = cardColor,
                        textColor = textColor,
                        fontFamily = fontFamily,
                        cardWidth = cardWidth,
                        cardHeight = cardHeight,
                        fontSize = fontSize
                    )

                    Text(
                        text = ":",
                        color = textColor,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )

                    FlipCard(
                        value = timeTriple.third,
                        cardColor = cardColor,
                        textColor = textColor,
                        fontFamily = fontFamily,
                        cardWidth = secondsCardWidth,
                        cardHeight = secondsCardHeight,
                        fontSize = secondsFontSize
                    )
                }

                // Date display
                Text(
                    text = dateString,
                    color = textColor.copy(alpha = 0.8f),
                    fontSize = dateFontSize,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily
                )
            }
        }
    }
}

@Composable
private fun FlipCard(
    value: String,
    cardColor: Color,
    textColor: Color,
    fontFamily: FontFamily,
    cardWidth: androidx.compose.ui.unit.Dp,
    cardHeight: androidx.compose.ui.unit.Dp,
    fontSize: androidx.compose.ui.unit.TextUnit
) {
    val corner = (cardHeight.value / 10f).dp

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
                .size(cardWidth, cardHeight)
                .background(
                    color = cardColor,
                    shape = RoundedCornerShape(corner)
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