package com.eid.onstand.feature.preview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eid.onstand.core.data.CustomizationRepository
import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreviewViewModel(
    private val customizationRepository: CustomizationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PreviewUiState())
    val uiState: StateFlow<PreviewUiState> = _uiState.asStateFlow()

    val customizationState = customizationRepository.customizationState

    init {
        loadCustomizationOptions()
        // Load saved customization state
        viewModelScope.launch {
            customizationRepository.loadCustomizationState()
        }
        // For now, just get the initial state without launching a coroutine
        _uiState.value = _uiState.value.copy(
            customizationState = customizationRepository.customizationState.value
        )
    }

    private fun loadCustomizationOptions() {
        _uiState.value = _uiState.value.copy(
            backgroundOptions = customizationRepository.getBackgroundOptions(),
            gradientOptions = customizationRepository.getGradientOptions(),
            staticColorOptions = customizationRepository.getStaticColorOptions(),
            clockTypes = customizationRepository.getClockTypes(),
            fontColorOptions = customizationRepository.getFontColorOptions(),
            layoutOptions = customizationRepository.getLayoutOptions()
        )

        // Set default selections using the new BackgroundType methods
        val defaultBackgroundType = customizationRepository.getBackgroundTypes().first()
        val defaultClockType = customizationRepository.getClockTypes().first()
        val defaultFontColor = customizationRepository.getFontColorOptions().first()
        val defaultLayout = customizationRepository.getLayoutOptions().first()

        customizationRepository.updateCustomizationState(
            CustomizationState(
                selectedBackground = defaultBackgroundType,
                selectedClockType = defaultClockType,
                selectedFontColor = defaultFontColor,
                selectedLayout = defaultLayout
            )
        )
    }

    fun selectBackground(background: BackgroundOption) {
        customizationRepository.selectBackground(background)
        // Manually update the UI state
        _uiState.value = _uiState.value.copy(
            customizationState = customizationRepository.customizationState.value
        )
    }

    fun selectClockType(clockType: ClockType) {
        customizationRepository.selectClockType(clockType)
        // Manually update the UI state
        _uiState.value = _uiState.value.copy(
            customizationState = customizationRepository.customizationState.value
        )
    }

    fun toggleSeconds(showSeconds: Boolean) {
        val currentClockType = customizationRepository.customizationState.value.selectedClockType
        if (currentClockType?.isDigital == true) {
            val updatedClockType = when (currentClockType) {
                is ClockType.Digital -> currentClockType.copy(showSeconds = showSeconds)
                is ClockType.DigitalSegments -> currentClockType.copy(showSeconds = showSeconds)
                is ClockType.Flip -> currentClockType.copy(showSeconds = showSeconds)
                is ClockType.MorphFlip -> currentClockType.copy(showSeconds = showSeconds)
                else -> currentClockType
            }

            customizationRepository.selectClockType(updatedClockType)
            // Manually update the UI state
            _uiState.value = _uiState.value.copy(
                customizationState = customizationRepository.customizationState.value
            )
        }
    }

    fun selectFontColor(fontColor: FontColorOption) {
        customizationRepository.selectFontColor(fontColor)
        // Manually update the UI state
        _uiState.value = _uiState.value.copy(
            customizationState = customizationRepository.customizationState.value
        )
    }

    fun selectLayout(layout: LayoutOption) {
        customizationRepository.selectLayout(layout)
        // Manually update the UI state
        _uiState.value = _uiState.value.copy(
            customizationState = customizationRepository.customizationState.value
        )
    }

    fun applyCustomization() {
        viewModelScope.launch {
            // Save the customization state
            customizationRepository.saveCustomizationState()
            _uiState.value = _uiState.value.copy(
                isCustomizationApplied = true
            )
        }
    }

    fun cancelCustomization() {
        // Reset to previous state or defaults
        loadCustomizationOptions()
        _uiState.value = _uiState.value.copy(
            isCustomizationApplied = false
        )
    }

    fun toggleCustomizationMode() {
        _uiState.value = _uiState.value.copy(
            isInCustomizationMode = !_uiState.value.isInCustomizationMode
        )
    }
}

data class PreviewUiState(
    val backgroundOptions: List<BackgroundOption> = emptyList(),
    val gradientOptions: List<BackgroundOption> = emptyList(),
    val staticColorOptions: List<BackgroundOption> = emptyList(),
    val clockTypes: List<ClockType> = emptyList(),
    val fontColorOptions: List<FontColorOption> = emptyList(),
    val layoutOptions: List<LayoutOption> = emptyList(),
    val customizationState: CustomizationState = CustomizationState(),
    val isInCustomizationMode: Boolean = false,
    val isCustomizationApplied: Boolean = false,
    val isLoading: Boolean = false
)