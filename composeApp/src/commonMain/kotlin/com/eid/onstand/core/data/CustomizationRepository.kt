package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull

class CustomizationRepository(
    private val backgroundRepository: BackgroundRepository,
    private val clockRepository: ClockRepository,
    private val customizationDataSource: CustomizationDataSource
) {

    private val _customizationState = MutableStateFlow(CustomizationState())
    val customizationState: StateFlow<CustomizationState> = _customizationState

    // New BackgroundType methods
    fun getBackgroundTypes(): List<BackgroundType> = backgroundRepository.getBackgroundTypes()

    fun getBackgroundOptions(): List<BackgroundOption> = backgroundRepository.getBackgroundOptions()
    fun getGradientOptions(): List<BackgroundOption> = backgroundRepository.getGradientOptions()
    fun getStaticColorOptions(): List<BackgroundOption> =
        backgroundRepository.getStaticColorOptions()

    fun getClockTypes(): List<ClockType> = clockRepository.getClockTypes()
    fun getFontFamilies(): List<FontFamily> = clockRepository.getFontFamilies()
    fun getClockColors(): List<ClockColor> = clockRepository.getClockColors()

    suspend fun initializeWithSavedState() {
        loadCustomizationState()
    }

    fun updateCustomizationState(newState: CustomizationState) {
        _customizationState.value = newState
    }

    // Legacy method for backward compatibility
    suspend fun selectBackground(background: BackgroundOption) {
        // Convert BackgroundOption to BackgroundType for now
        // This is a temporary solution during migration
        val backgroundType = convertBackgroundOptionToType(background)
        _customizationState.value = _customizationState.value.copy(
            selectedBackground = backgroundType
        )
        saveCustomizationState()
    }

    suspend fun selectClockType(clockType: ClockType) {
        _customizationState.value = _customizationState.value.copy(
            selectedClockType = clockType
        )
        saveCustomizationState()
    }

    suspend fun selectFont(font: FontFamily) {
        _customizationState.value = _customizationState.value.copy(
            selectedFont = font
        )
        saveCustomizationState()
    }

    suspend fun selectColor(color: ClockColor) {
        _customizationState.value = _customizationState.value.copy(
            selectedColor = color
        )
        saveCustomizationState()
    }

    suspend fun saveCustomizationState() {
        val currentState = _customizationState.value
        val serializableState = SerializableCustomizationState(
            backgroundId = currentState.selectedBackground?.name,
            backgroundType = getBackgroundTypeString(currentState.selectedBackground),
            clockTypeId = currentState.selectedClockType?.name,
            clockTypeName = currentState.selectedClockType?.name,
            selectedFont = currentState.selectedFont?.systemName,
            selectedColorName = currentState.selectedColor?.name,
        )
        customizationDataSource.saveCustomizationState(serializableState)
    }

    suspend fun loadCustomizationState() {
        val savedState = customizationDataSource.getCustomizationState().firstOrNull()
        if (savedState != null) {
            val customizationState = convertToCustomizationState(savedState)
            _customizationState.value = customizationState
        }
    }

    private fun getBackgroundTypeString(background: BackgroundType?): String? {
        return when (background) {
            is BackgroundType.Solid -> "solid"
            is BackgroundType.Gradient -> "gradient"
            is BackgroundType.Shader -> "shader"
            is BackgroundType.Live -> "live"
            is BackgroundType.Pattern -> "pattern"
            null -> null
        }
    }

    private fun convertToCustomizationState(savedState: SerializableCustomizationState): CustomizationState {
        val background =
            findBackgroundByNameAndType(savedState.backgroundId, savedState.backgroundType)
        val clockType = findClockTypeByName(savedState.clockTypeId)
        val font = findFontBySystemName(savedState.selectedFont)
        val color = findColorByName(savedState.selectedColorName)

        return CustomizationState(
            selectedBackground = background,
            selectedClockType = clockType,
            selectedFont = font,
            selectedColor = color,
        )
    }

    private fun findBackgroundByNameAndType(name: String?, type: String?): BackgroundType? {
        if (name == null) return null
        return getBackgroundTypes().find { it.name == name }
    }

    private fun findClockTypeByName(name: String?): ClockType? {
        if (name == null) return null
        return getClockTypes().find { it.name == name }
    }

    private fun findFontBySystemName(systemName: String?): FontFamily? {
        if (systemName == null) return null
        return getFontFamilies().find { it.systemName == systemName }
    }

    private fun findColorByName(name: String?): ClockColor? {
        if (name == null) return null
        return getClockColors().find { it.name == name }
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