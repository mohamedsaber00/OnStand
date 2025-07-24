package com.eid.onstand.feature.widgets.clocks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.ui.theme.BlurConstants
import com.eid.onstand.core.ui.theme.ColorConstants
import com.eid.onstand.core.models.FontFamily as AppFontFamily
import com.eid.onstand.core.utils.toComposeFontFamily
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

/**
 * A composable function for a very basic clock widget.
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
    fontFamily: AppFontFamily = AppFontFamily.ROBOTO,
    textColor: Color = Color.White,
    isPreview: Boolean = false,
    hazeState: HazeState? = null
) {
    val timePattern = if (showSeconds) "HH:mm:ss" else "HH:mm"
    val currentTimeString =
        currentTime.format(LocalDateTime.Format { byUnicodePattern(timePattern) })


    Card(
        modifier = Modifier
            .width(400.dp)
            .aspectRatio(2.5f)
            .clip(RoundedCornerShape(32.dp))
            .hazeEffect(
                state = hazeState,
                style = BlurConstants.MIN_BLUR_HAZE_STYLE
            ),
        colors = CardDefaults.cardColors(
            containerColor = ColorConstants.TRANSPARENT
        ),
    ) {

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val scaleFactor = if (isPreview) 0.6f else 1f
            val numChars = if (showSeconds) 8 else 5
            val digitWidth = maxWidth / (numChars.toFloat() * 1.2f) * scaleFactor
            val colonWidth = digitWidth * 0.6f

            // Calculate font size based on both width and height constraints
            val widthBasedFontSize = (digitWidth.value * 1.7f).sp
            val heightBasedFontSize = (maxHeight.value * 0.8f * scaleFactor).sp
            val fontSize = minOf(widthBasedFontSize.value, heightBasedFontSize.value).sp

            Row(
                modifier = Modifier.fillMaxSize()
                    .padding(vertical = maxHeight * 0.05f * scaleFactor)
                    .clip(RoundedCornerShape(maxWidth * 0.02f * scaleFactor)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                currentTimeString.forEach { char ->
                    val isColon = char == ':'
                    val width = digitWidth

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
                            fontFamily = fontFamily.toComposeFontFamily(),
                            textAlign = TextAlign.Center,
                      //      modifier = Modifier.width(width)
                        )
                    }
                }
            }
        }
    }
}
