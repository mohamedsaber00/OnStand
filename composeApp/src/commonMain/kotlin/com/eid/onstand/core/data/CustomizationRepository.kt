package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CustomizationRepository(
    private val backgroundRepository: BackgroundRepository,
    private val clockRepository: ClockRepository,
    private val userPreferencesRepository: UserPreferencesRepository
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

    suspend fun initializeWithSavedState() {
        val savedState = userPreferencesRepository.loadCustomizationState()
        if (savedState != null) {
            _customizationState.value = savedState
        } else {
            _customizationState.value = userPreferencesRepository.getDefaultCustomizationState()
        }
    }

    fun updateCustomizationState(newState: CustomizationState) {
        _customizationState.value = newState
    }

    suspend fun saveCurrentState() {
        userPreferencesRepository.saveCustomizationState(_customizationState.value)
    }

    suspend fun applyAndSaveCustomization() {
        saveCurrentState()
    }

    suspend fun cancelCustomization() {
        val savedState = userPreferencesRepository.loadCustomizationState()
        if (savedState != null) {
            _customizationState.value = savedState
        } else {
            _customizationState.value = userPreferencesRepository.getDefaultCustomizationState()
        }
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

    suspend fun saveCustomizationState() {
        userPreferencesRepository.saveCustomizationState(_customizationState.value)
    }

    suspend fun loadCustomizationState() {
        val savedState = userPreferencesRepository.loadCustomizationState()
        if (savedState != null) {
            _customizationState.value = savedState
        }
    }

    // Helper method to convert BackgroundOption to BackgroundType during migration
    private fun convertBackgroundOptionToType(option: BackgroundOption): BackgroundType {
        return when (option) {
            is BackgroundOption.SolidColor -> BackgroundType.Solid(
                name = option.name,
                previewColor = option.previewColor,
                color = option.color
            )

            is BackgroundOption.Gradient -> BackgroundType.Gradient(
                name = option.name,
                previewColor = option.previewColor,
                colors = option.colors,
                direction = GradientDirection.VERTICAL
            )

            is BackgroundOption.Shader -> BackgroundType.Shader(
                name = option.name,
                previewColor = option.previewColor,
                shaderType = option.shaderType
            )

            is BackgroundOption.Live -> BackgroundType.Live(
                name = option.name,
                previewColor = option.previewColor,
                animationType = option.animationType
            )

            is BackgroundOption.Abstract -> BackgroundType.Pattern(
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