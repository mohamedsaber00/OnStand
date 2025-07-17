package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CustomizationRepository(
    private val backgroundRepository: BackgroundRepository,
    private val clockRepository: ClockRepository
) {

    private val _customizationState = MutableStateFlow(CustomizationState())
    val customizationState: StateFlow<CustomizationState> = _customizationState

    // New BackgroundType methods
    fun getBackgroundTypes(): List<BackgroundType> = backgroundRepository.getBackgroundTypes()
    fun getGradientTypes(): List<BackgroundType> = backgroundRepository.getGradientTypes()
    fun getSolidTypes(): List<BackgroundType> = backgroundRepository.getSolidTypes()
    fun getPatternTypes(): List<BackgroundType> = backgroundRepository.getPatternTypes()

    // Legacy BackgroundOption methods for backward compatibility
    fun getBackgroundOptions(): List<BackgroundOption> = backgroundRepository.getBackgroundOptions()
    fun getGradientOptions(): List<BackgroundOption> = backgroundRepository.getGradientOptions()
    fun getStaticColorOptions(): List<BackgroundOption> =
        backgroundRepository.getStaticColorOptions()

    fun getClockTypes(): List<ClockType> = clockRepository.getClockTypes()
    fun getFontColorOptions(): List<FontColorOption> = clockRepository.getFontColorOptions()
    fun getLayoutOptions(): List<LayoutOption> = clockRepository.getLayoutOptions()

    fun updateCustomizationState(newState: CustomizationState) {
        _customizationState.value = newState
    }

    fun selectBackgroundType(backgroundType: BackgroundType) {
        _customizationState.value = _customizationState.value.copy(
            selectedBackground = backgroundType
        )
    }

    // Legacy method for backward compatibility
    fun selectBackground(background: BackgroundOption) {
        // Convert BackgroundOption to BackgroundType for now
        // This is a temporary solution during migration
        val backgroundType = convertBackgroundOptionToType(background)
        _customizationState.value = _customizationState.value.copy(
            selectedBackground = backgroundType
        )
    }

    fun selectClockType(clockType: ClockType) {
        _customizationState.value = _customizationState.value.copy(
            selectedClockType = clockType
        )
    }

    fun selectFontColor(fontColor: FontColorOption) {
        _customizationState.value = _customizationState.value.copy(
            selectedFontColor = fontColor
        )
    }

    fun selectLayout(layout: LayoutOption) {
        _customizationState.value = _customizationState.value.copy(
            selectedLayout = layout
        )
    }

    // Helper method to convert BackgroundOption to BackgroundType during migration
    private fun convertBackgroundOptionToType(option: BackgroundOption): BackgroundType {
        return when (option) {
            is BackgroundOption.SolidColor -> {
                when (option.id) {
                    "solid_black" -> BackgroundType.Solid(
                        id = "black",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    "solid_white" -> BackgroundType.Solid(
                        id = "white",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    "solid_gray" -> BackgroundType.Solid(
                        id = "gray",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    "solid_dark_gray" -> BackgroundType.Solid(
                        id = "dark_gray",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    "solid_blue" -> BackgroundType.Solid(
                        id = "blue",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    "solid_green" -> BackgroundType.Solid(
                        id = "green",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    "solid_purple" -> BackgroundType.Solid(
                        id = "purple",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    "solid_red" -> BackgroundType.Solid(
                        id = "red",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    "static_category" -> BackgroundType.Solid(
                        id = "black",
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )

                    else -> BackgroundType.Solid(
                        id = option.id,
                        name = option.name,
                        previewColor = option.previewColor,
                        color = option.color
                    )
                }
            }

            is BackgroundOption.Gradient -> {
                when (option.id) {
                    "gradient_sunset" -> BackgroundType.Gradient(
                        id = "sunset",
                        name = option.name,
                        previewColor = option.previewColor,
                        colors = option.colors,
                        direction = GradientDirection.DIAGONAL_UP
                    )

                    "gradient_ocean" -> BackgroundType.Gradient(
                        id = "ocean",
                        name = option.name,
                        previewColor = option.previewColor,
                        colors = option.colors,
                        direction = GradientDirection.VERTICAL
                    )

                    "gradient_forest" -> BackgroundType.Gradient(
                        id = "forest",
                        name = option.name,
                        previewColor = option.previewColor,
                        colors = option.colors,
                        direction = GradientDirection.DIAGONAL_DOWN
                    )

                    "gradient_purple" -> BackgroundType.Gradient(
                        id = "purple",
                        name = option.name,
                        previewColor = option.previewColor,
                        colors = option.colors,
                        direction = GradientDirection.VERTICAL
                    )

                    "gradient_fire" -> BackgroundType.Gradient(
                        id = "fire",
                        name = option.name,
                        previewColor = option.previewColor,
                        colors = option.colors,
                        direction = GradientDirection.RADIAL
                    )

                    "gradient_mint" -> BackgroundType.Gradient(
                        id = "mint",
                        name = option.name,
                        previewColor = option.previewColor,
                        colors = option.colors,
                        direction = GradientDirection.HORIZONTAL
                    )

                    "gradient_category" -> BackgroundType.Gradient(
                        id = "sunset",
                        name = option.name,
                        previewColor = option.previewColor,
                        colors = option.colors,
                        direction = GradientDirection.VERTICAL
                    )

                    else -> BackgroundType.Gradient(
                        id = option.id,
                        name = option.name,
                        previewColor = option.previewColor,
                        colors = option.colors,
                        direction = GradientDirection.VERTICAL
                    )
                }
            }

            is BackgroundOption.Shader -> {
                when (option.id) {
                    "shader_ether" -> BackgroundType.Shader(
                        id = "ether",
                        name = option.name,
                        previewColor = option.previewColor,
                        shaderType = option.shaderType
                    )

                    "shader_space" -> BackgroundType.Shader(
                        id = "space",
                        name = option.name,
                        previewColor = option.previewColor,
                        shaderType = option.shaderType
                    )

                    "shader_glowing_ring" -> BackgroundType.Shader(
                        id = "glowing_ring",
                        name = option.name,
                        previewColor = option.previewColor,
                        shaderType = option.shaderType
                    )

                    "shader_purple_gradient" -> BackgroundType.Shader(
                        id = "purple_flow",
                        name = option.name,
                        previewColor = option.previewColor,
                        shaderType = option.shaderType
                    )

                    "shader_triangles" -> BackgroundType.Shader(
                        id = "moving_triangles",
                        name = option.name,
                        previewColor = option.previewColor,
                        shaderType = option.shaderType
                    )

                    "shader_moving_waves" -> BackgroundType.Shader(
                        id = "moving_waves",
                        name = option.name,
                        previewColor = option.previewColor,
                        shaderType = option.shaderType
                    )

                    "shader_turbulence" -> BackgroundType.Shader(
                        id = "turbulence",
                        name = option.name,
                        previewColor = option.previewColor,
                        shaderType = option.shaderType
                    )

                    else -> BackgroundType.Shader(
                        id = option.id,
                        name = option.name,
                        previewColor = option.previewColor,
                        shaderType = option.shaderType
                    )
                }
            }

            is BackgroundOption.Live -> {
                when (option.id) {
                    "live_rotating_gradient" -> BackgroundType.Live(
                        id = "rotating_gradient",
                        name = option.name,
                        previewColor = option.previewColor,
                        animationType = option.animationType
                    )

                    "live_fog" -> BackgroundType.Live(
                        id = "fog_effect",
                        name = option.name,
                        previewColor = option.previewColor,
                        animationType = option.animationType
                    )

                    "live_animated_waves" -> BackgroundType.Live(
                        id = "animated_particles",
                        name = option.name,
                        previewColor = option.previewColor,
                        animationType = option.animationType
                    )

                    else -> BackgroundType.Live(
                        id = option.id,
                        name = option.name,
                        previewColor = option.previewColor,
                        animationType = option.animationType
                    )
                }
            }

            is BackgroundOption.Abstract -> BackgroundType.Pattern(
                id = option.id,
                name = option.name,
                previewColor = option.previewColor,
                patternType = when (option.patternType) {
                    AbstractPatternType.GEOMETRIC -> PatternType.GEOMETRIC
                    AbstractPatternType.ORGANIC -> PatternType.ORGANIC
                    AbstractPatternType.MINIMAL -> PatternType.MINIMAL
                    AbstractPatternType.ARTISTIC -> PatternType.ARTISTIC
                },
                primaryColor = option.previewColor,
                secondaryColor = option.previewColor.copy(alpha = 0.7f)
            )
        }
    }
}