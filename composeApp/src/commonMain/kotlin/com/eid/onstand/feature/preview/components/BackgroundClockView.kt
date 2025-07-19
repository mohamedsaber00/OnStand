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
import androidx.compose.ui.unit.dp
import com.eid.onstand.core.models.*
import com.eid.onstand.core.theme.BlurConstants
import com.eid.onstand.core.theme.ColorConstants
import com.eid.onstand.core.theme.GradientConstants
import com.eid.onstand.feature.backgrounds.compose.AnimatedBackground
import com.eid.onstand.feature.backgrounds.compose.FoggyBackground
import com.eid.onstand.feature.backgrounds.compose.RotatingGradientBackground
import com.eid.onstand.feature.backgrounds.shader.*
import com.eid.onstand.feature.widgets.clocks.*
import com.mikepenz.hypnoticcanvas.shaderBackground
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.delay
import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalTime::class)
@Composable
fun BackgroundClockView(
    backgroundType: BackgroundType? = null,
    clockType: ClockType? = null,
    fontColorOption: FontColorOption? = null,
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf(Clock.System.now() )}


    val hazeState = rememberHazeState()


    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Clock.System.now()
            delay(1000)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Background content - this is the haze source
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
                .then(
                    // Apply shader background if it's a shader type
                    when (backgroundType) {
                        is BackgroundType.Shader -> {
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

                                ShaderType.TURBULENCE -> Modifier.shaderBackground(TurbulenceShader)
                            }
                        }

                        else -> Modifier.background(getBackgroundBrush(backgroundType))
                    }
                )
        ) {
            // Render animated backgrounds (non-shader)
            when (backgroundType) {
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
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        else -> {}
                    }
                }

                is BackgroundType.Gradient -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(getBackgroundBrush(backgroundType))
                    ) {}
                }

                else -> {}
            }
        }

        // Clock card - this applies the haze effect
        // Use a custom HazeStyle for minimum blur
        Card(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(2.5f)
                // Width:Height ratio for a more rectangular card
                .clip(RoundedCornerShape(32.dp))
                .hazeEffect(
                    state = hazeState,
                    style = BlurConstants.MIN_BLUR_HAZE_STYLE
                )
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(
                containerColor = ColorConstants.TRANSPARENT
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val timeZone = TimeZone.currentSystemDefault()
                val localTime = currentTime.toLocalDateTime(timeZone)

                // Render the actual clock widget based on type
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
                            )
                        )
                    }



                    is ClockType.MorphFlip -> {
                        MorphFlipClockWidget(
                            currentTime = localTime,
                            cardColor = ColorConstants.DEFAULT_MORPH_CARD_COLOR,
                            textColor = fontColorOption?.primaryColor ?: Color.Black,
                            fontFamily = getFontFamily(clockType.fontFamily)
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
                                ?: ColorConstants.DEFAULT_TEXT_COLOR).copy(alpha = ColorConstants.DEFAULT_NUMBERS_COLOR_ALPHA)
                        )
                    }

                    is ClockType.Digital -> {
                        BasicClockWidget(
                            currentTime = localTime,
                            showSeconds = clockType.showSeconds,
                            fontFamily = getFontFamily(clockType.fontFamily),
                            textColor = fontColorOption?.primaryColor
                                ?: ColorConstants.DEFAULT_TEXT_COLOR,
                            modifier = Modifier

                        )
                    }

                    null -> {
                        // Default fallback
                        BasicClockWidget(
                            currentTime = localTime,
                            showSeconds = false,
                            fontFamily = getFontFamily("Roboto"),
                            textColor = fontColorOption?.primaryColor
                                ?: ColorConstants.DEFAULT_TEXT_COLOR,
                            modifier = Modifier
                        )

                    }
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
private fun getBackgroundBrush(backgroundType: BackgroundType?): Brush {
    return when (backgroundType) {
        is BackgroundType.Gradient -> {
            if (backgroundType.colors.isNotEmpty()) {
                when (backgroundType.direction) {
                    GradientDirection.VERTICAL -> Brush.verticalGradient(backgroundType.colors)
                    GradientDirection.HORIZONTAL -> Brush.horizontalGradient(backgroundType.colors)
                    GradientDirection.DIAGONAL_UP -> Brush.linearGradient(
                        colors = backgroundType.colors,
                        start = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY),
                        end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, 0f)
                    )

                    GradientDirection.DIAGONAL_DOWN -> Brush.linearGradient(
                        colors = backgroundType.colors,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(
                            Float.POSITIVE_INFINITY,
                            Float.POSITIVE_INFINITY
                        )
                    )

                    GradientDirection.RADIAL -> Brush.radialGradient(backgroundType.colors)
                }
            } else {
                Brush.linearGradient(
                    listOf(backgroundType.previewColor, backgroundType.previewColor)
                )
            }
        }

        is BackgroundType.Solid -> {
            Brush.linearGradient(
                listOf(backgroundType.color, backgroundType.color)
            )
        }

        is BackgroundType.Pattern -> {
            Brush.linearGradient(
                listOf(
                    backgroundType.primaryColor,
                    backgroundType.secondaryColor ?: backgroundType.primaryColor
                )
            )
        }

        is BackgroundType.Live -> {
            // For live backgrounds, use transparent so animation shows through
            GradientConstants.TRANSPARENT_GRADIENT
        }

        is BackgroundType.Shader -> {
            // For shader backgrounds, use transparent since shader is applied via modifier
            GradientConstants.TRANSPARENT_GRADIENT
        }

        null -> {
            GradientConstants.BLACK_GRADIENT
        }
    }
}