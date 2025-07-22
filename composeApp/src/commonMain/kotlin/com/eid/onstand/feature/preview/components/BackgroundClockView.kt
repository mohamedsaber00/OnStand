package com.eid.onstand.feature.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.eid.onstand.core.models.*
import com.eid.onstand.core.shaders.shaderBackground
import com.eid.onstand.core.theme.GradientConstants
import com.eid.onstand.feature.backgrounds.compose.AnimatedBackground
import com.eid.onstand.feature.backgrounds.compose.FoggyBackground
import com.eid.onstand.feature.backgrounds.compose.RotatingGradientBackground
import com.eid.onstand.feature.backgrounds.shader.*
import com.eid.onstand.feature.widgets.clocks.*
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
    fontFamily: FontFamily? = null,
    clockColor: ClockColor? = null,
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf(Clock.System.now()) }


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
                                ShaderType.PALETTE -> Modifier.shaderBackground(PaletteShader)
                                ShaderType.RED -> Modifier.shaderBackground(RedShader)
                                ShaderType.MOVING_WAVES -> Modifier.shaderBackground(
                                    MovingWaveShader
                                )

                                ShaderType.PURPLE_SMOKE -> Modifier.shaderBackground(
                                    PurpleSmokeShader
                                )
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