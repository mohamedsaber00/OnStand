package com.eid.onstand.feature.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.*
import com.eid.onstand.feature.backgrounds.compose.AnimatedBackground
import com.eid.onstand.feature.backgrounds.compose.FoggyBackground
import com.eid.onstand.feature.backgrounds.compose.RotatingGradientBackground
import com.eid.onstand.feature.backgrounds.shader.*
import com.eid.onstand.feature.widgets.clocks.*
import com.mikepenz.hypnoticcanvas.shaderBackground
import kotlinx.coroutines.delay
import kotlinx.datetime.*

@Composable
fun ClockPreview(
    backgroundOption: BackgroundOption? = null,
    clockStyle: ClockStyle? = null,
    fontColorOption: FontColorOption? = null,
    layoutOption: LayoutOption? = null,
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf(Clock.System.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Clock.System.now()
            delay(1000)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    // Apply shader background if it's a shader type
                    when (backgroundOption) {
                        is BackgroundOption.Shader -> {
                            when (backgroundOption.shaderType) {
                                ShaderType.ETHER -> Modifier.shaderBackground(EtherShader)
                                ShaderType.GLOWING_RING -> Modifier.shaderBackground(GlowingRing)
                                ShaderType.MOVING_TRIANGLES -> Modifier.shaderBackground(
                                    MovingTrianglesShader
                                )

                                ShaderType.PURPLE_GRADIENT -> Modifier.shaderBackground(
                                    PurpleGradientShader
                                )

                                ShaderType.SPACE -> Modifier.shaderBackground(SpaceShader)
                            }
                        }

                        else -> Modifier.background(getBackgroundBrush(backgroundOption))
                    }
                )
        ) {
            // Render animated backgrounds (non-shader)
            when (backgroundOption) {
                is BackgroundOption.Live -> {
                    when (backgroundOption.animationType) {
                        LiveAnimationType.ROTATING_GRADIENT -> {
                            RotatingGradientBackground()
                        }

                        LiveAnimationType.FOG_EFFECT -> {
                            FoggyBackground(
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        LiveAnimationType.ANIMATED_PARTICLES -> {
                            AnimatedBackground(
                                modifier = Modifier.fillMaxSize(),
                                colors = listOf(
                                    Color(0xFF4A90E2),
                                    Color(0xFF7B68EE),
                                    Color(0xFF9B59B6)
                                )
                            )
                        }

                        else -> {}
                    }
                }

                is BackgroundOption.Gradient -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(getBackgroundBrush(backgroundOption))
                    ) {}
                }

                else -> {}
            }

            // Clock Content
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val timeZone = TimeZone.currentSystemDefault()
                val localTime = currentTime.toLocalDateTime(timeZone)

                // Previous minutes (if enabled)
                if (layoutOption?.showPreviousMinutes == true) {
                    val previousMinute = if (localTime.minute == 0) 59 else localTime.minute - 1
                    val nextMinute = if (localTime.minute == 59) 0 else localTime.minute + 1

                    Text(
                        text = "${
                            previousMinute.toString().padStart(2, '0')
                        } ${nextMinute.toString().padStart(2, '0')}",
                        fontSize = 14.sp,
                        color = (fontColorOption?.secondaryColor ?: Color.Gray).copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }

                // Render the actual clock widget based on style
                when (clockStyle?.id) {
                    "digital_segments" -> {
                        DigitalSegmentClock(
                            currentTime = localTime,
                            showSeconds = clockStyle.showSeconds,
                            activeColor = fontColorOption?.primaryColor ?: Color.White,
                            inactiveColor = (fontColorOption?.primaryColor ?: Color.White).copy(
                                alpha = 0.1f
                            )
                        )
                    }
                    "flip_clock" -> {
                        FlipClockWidget(
                            currentTime = localTime,
                            cardColor = Color.Black.copy(alpha = 0.6f),
                            textColor = fontColorOption?.primaryColor ?: Color.White,
                            fontFamily = getFontFamily(clockStyle.fontFamily)
                        )
                    }
                    "analog_classic" -> {
                        AnalogClockWidget(
                            currentTime = localTime,
                            clockColor = fontColorOption?.primaryColor ?: Color.White,
                            handsColor = fontColorOption?.primaryColor ?: Color.White,
                            numbersColor = (fontColorOption?.primaryColor
                                ?: Color.White).copy(alpha = 0.8f)
                        )
                    }
                    else -> {
                        // For other digital clocks, use ClockWidget
                        ClockWidget(
                            currentTime = localTime,
                            showSeconds = clockStyle?.showSeconds ?: false,
                            fontFamily = getFontFamily(clockStyle?.fontFamily ?: "Roboto"),
                            textColor = fontColorOption?.primaryColor ?: Color.White
                        )
                    }
                }

                // Date (if enabled)
                if (clockStyle?.showDate == true) {
                    val dateFormat = "${
                        localTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
                    }, ${
                        localTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
                    } ${localTime.dayOfMonth}"
                    Text(
                        text = dateFormat,
                        fontSize = 14.sp,
                        color = fontColorOption?.secondaryColor ?: Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
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

@Composable
private fun getBackgroundBrush(backgroundOption: BackgroundOption?): Brush {
    return when (backgroundOption) {
        is BackgroundOption.Gradient -> {
            if (backgroundOption.colors.isNotEmpty()) {
                Brush.linearGradient(
                    colors = backgroundOption.colors
                )
            } else {
                Brush.linearGradient(
                    listOf(backgroundOption.previewColor, backgroundOption.previewColor)
                )
            }
        }
        is BackgroundOption.SolidColor -> {
            Brush.linearGradient(
                listOf(backgroundOption.color, backgroundOption.color)
            )
        }
        is BackgroundOption.Abstract -> {
            Brush.linearGradient(
                listOf(backgroundOption.previewColor, backgroundOption.previewColor)
            )
        }
        is BackgroundOption.Live -> {
            // For live backgrounds, use transparent so animation shows through
            Brush.linearGradient(
                listOf(Color.Transparent, Color.Transparent)
            )
        }
        is BackgroundOption.Shader -> {
            // For shader backgrounds, use transparent since shader is applied via modifier
            Brush.linearGradient(
                listOf(Color.Transparent, Color.Transparent)
            )
        }
        null -> {
            Brush.linearGradient(
                listOf(Color.Black, Color.Black)
            )
        }
    }
}