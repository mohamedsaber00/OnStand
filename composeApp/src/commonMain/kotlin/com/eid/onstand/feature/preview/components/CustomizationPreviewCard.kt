package com.eid.onstand.feature.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.eid.onstand.core.models.*
import com.eid.onstand.core.theme.ColorConstants
import com.eid.onstand.core.theme.GradientConstants
import com.eid.onstand.feature.backgrounds.compose.AnimatedBackground
import com.eid.onstand.feature.backgrounds.compose.FoggyBackground
import com.eid.onstand.feature.backgrounds.compose.RotatingGradientBackground
import com.eid.onstand.feature.backgrounds.shader.*
import com.eid.onstand.feature.widgets.clocks.*
import com.mikepenz.hypnoticcanvas.shaderBackground
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun CustomizationPreviewCard(
    backgroundType: BackgroundType?,
    clockType: ClockType?,
    fontColorOption: FontColorOption?,
    layoutOption: LayoutOption?,
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf(Clock.System.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Clock.System.now()
            delay(1000)
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Background for the clock preview
        when (backgroundType) {
            is BackgroundType.Solid -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = backgroundType.color)
                )
            }

            is BackgroundType.Gradient -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = if (backgroundType.colors.isNotEmpty()) {
                                Brush.linearGradient(
                                    colors = backgroundType.colors,
                                    tileMode = TileMode.Mirror
                                )
                            } else {
                                Brush.linearGradient(
                                    colors = listOf(
                                        backgroundType.previewColor,
                                        backgroundType.previewColor
                                    )
                                )
                            }
                        )
                )
            }

            is BackgroundType.Shader -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            when (backgroundType.shaderType) {
                                ShaderType.ETHER -> Modifier.shaderBackground(EtherShader)
                                ShaderType.GLOWING_RING -> Modifier.shaderBackground(GlowingRing)
                                ShaderType.MOVING_TRIANGLES -> Modifier.shaderBackground(
                                    MovingTrianglesShader
                                )

                                ShaderType.PURPLE_GRADIENT -> Modifier.shaderBackground(
                                    PurpleGradientShader
                                )

                                ShaderType.SPACE -> Modifier.shaderBackground(SpaceShader)
                                ShaderType.MOVING_WAVES -> Modifier.shaderBackground(
                                    MovingWaveShader
                                )

                                ShaderType.TURBULENCE -> Modifier.shaderBackground(
                                    TurbulenceShader
                                )
                            }
                        )
                )
            }

            is BackgroundType.Live -> {
                when (backgroundType.animationType) {
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
                            colors = GradientConstants.DEFAULT_GRADIENT_COLORS
                        )
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Black)
                        )
                    }
                }
            }

            is BackgroundType.Pattern -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    backgroundType.primaryColor,
                                    backgroundType.secondaryColor ?: backgroundType.primaryColor
                                )
                            )
                        )
                )
            }

            null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                )
            }
        }

        // Clock overlay - properly sized for preview
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val timeZone = TimeZone.currentSystemDefault()
            val localTime = currentTime.toLocalDateTime(timeZone)

            when (clockType) {
                is ClockType.DigitalSegments -> {
                    DigitalSegmentClock(
                        currentTime = localTime,
                        showSeconds = clockType.showSeconds,
                        activeColor = fontColorOption?.primaryColor
                            ?: ColorConstants.DEFAULT_TEXT_COLOR,
                        inactiveColor = (fontColorOption?.primaryColor
                            ?: ColorConstants.DEFAULT_TEXT_COLOR).copy(
                            alpha = ColorConstants.DEFAULT_INACTIVE_COLOR_ALPHA
                        ),
                        isPreview = true,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is ClockType.Flip -> {
                    FlipClockWidget(
                        currentTime = localTime,
                        cardColor = ColorConstants.DEFAULT_CARD_COLOR,
                        textColor = fontColorOption?.primaryColor
                            ?: ColorConstants.DEFAULT_TEXT_COLOR,
                        fontFamily = getFontFamily(clockType.fontFamily),
                        isPreview = true,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is ClockType.MorphFlip -> {
                    MorphFlipClockWidget(
                        currentTime = localTime,
                        cardColor = ColorConstants.DEFAULT_MORPH_CARD_COLOR,
                        textColor = fontColorOption?.primaryColor ?: Color.Black,
                        fontFamily = getFontFamily(clockType.fontFamily),
                        isPreview = true,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is ClockType.Analog -> {
                    AnalogClockWidget(
                        currentTime = localTime,
                        clockColor = fontColorOption?.primaryColor
                            ?: ColorConstants.DEFAULT_TEXT_COLOR,
                        handsColor = fontColorOption?.primaryColor
                            ?: ColorConstants.DEFAULT_TEXT_COLOR,
                        numbersColor = (fontColorOption?.primaryColor
                            ?: ColorConstants.DEFAULT_TEXT_COLOR).copy(
                            alpha = ColorConstants.DEFAULT_NUMBERS_COLOR_ALPHA
                        )
                    )
                }

                is ClockType.Digital -> {
                    BasicClockWidget(
                        currentTime = localTime,
                        showSeconds = clockType.showSeconds,
                        fontFamily = getFontFamily(clockType.fontFamily),
                        textColor = fontColorOption?.primaryColor
                            ?: ColorConstants.DEFAULT_TEXT_COLOR,
                        isPreview = true,
                        modifier = Modifier
                    )
                }

                null -> {
                    BasicClockWidget(
                        currentTime = localTime,
                        showSeconds = false,
                        fontFamily = getFontFamily("Roboto"),
                        textColor = fontColorOption?.primaryColor
                            ?: ColorConstants.DEFAULT_TEXT_COLOR,
                        isPreview = true,
                        modifier = Modifier
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