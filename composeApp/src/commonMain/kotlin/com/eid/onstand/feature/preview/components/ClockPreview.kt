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
    backgroundType: BackgroundType? = null,
    clockType: ClockType? = null,
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
                .fillMaxSize().align(Alignment.CenterHorizontally)
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

                is BackgroundType.Gradient -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(getBackgroundBrush(backgroundType))
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

                // Render the actual clock widget based on type
                when (clockType) {
                    is ClockType.DigitalSegments -> {
                        DigitalSegmentClock(
                            currentTime = localTime,
                            showSeconds = false,
                            activeColor = fontColorOption?.primaryColor ?: Color.White,
                            inactiveColor = (fontColorOption?.primaryColor ?: Color.White).copy(
                                alpha = 0.1f
                            )
                        )
                    }
                    is ClockType.Flip -> {
                        when (clockType.flipStyle) {
                            FlipStyle.MORPH -> {
                                MorphFlipClockWidget(
                                    currentTime = localTime,
                                    cardColor = Color(0xFFFFA77A).copy(alpha = 0.85f),
                                    textColor = fontColorOption?.primaryColor ?: Color.Black,
                                    fontFamily = getFontFamily(clockType.fontFamily)
                                )
                            }

                            else -> {
                                FlipClockWidget(
                                    currentTime = localTime,
                                    cardColor = Color.Black.copy(alpha = 0.6f),
                                    textColor = fontColorOption?.primaryColor ?: Color.White,
                                    fontFamily = getFontFamily(clockType.fontFamily)
                                )
                            }
                        }
                    }
                    is ClockType.Analog -> {
                        AnalogClockWidget(
                            currentTime = localTime,
                            clockColor = fontColorOption?.primaryColor ?: Color.White,
                            handsColor = fontColorOption?.primaryColor ?: Color.White,
                            numbersColor = (fontColorOption?.primaryColor
                                ?: Color.White).copy(alpha = 0.8f)
                        )
                    }
                    is ClockType.Digital, is ClockType.Minimal -> {
                        val showSeconds = when (clockType) {
                            is ClockType.Digital -> clockType.showSeconds
                            is ClockType.Minimal -> clockType.showSeconds
                            else -> false
                        }

                        ClockWidget(
                            currentTime = localTime,
                            showSeconds = showSeconds,
                            fontFamily = getFontFamily(clockType?.fontFamily ?: "Roboto"),
                            textColor = fontColorOption?.primaryColor ?: Color.White
                        )
                    }
                    null -> {
                        // Default fallback
                        ClockWidget(
                            currentTime = localTime,
                            showSeconds = false,
                            fontFamily = getFontFamily("Roboto"),
                            textColor = fontColorOption?.primaryColor ?: Color.White
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
            Brush.linearGradient(
                listOf(Color.Transparent, Color.Transparent)
            )
        }
        is BackgroundType.Shader -> {
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