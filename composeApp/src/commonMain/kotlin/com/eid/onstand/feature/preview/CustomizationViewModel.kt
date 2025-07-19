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

        // Update UI state with current customization state
        viewModelScope.launch {
            customizationRepository.customizationState.collect { state ->
                _uiState.value = _uiState.value.copy(
                    customizationState = state
                )
            }
        }
    }

    private fun loadCustomizationOptions() {
        _uiState.value = _uiState.value.copy(
            backgroundOptions = customizationRepository.getBackgroundOptions(),
            gradientOptions = customizationRepository.getGradientOptions(),
            staticColorOptions = customizationRepository.getStaticColorOptions(),
            clockTypes = customizationRepository.getClockTypes(),
            fontFamilies = customizationRepository.getFontFamilies(),
            clockColors = customizationRepository.getClockColors(),
        )
    }

    fun selectBackground(background: BackgroundOption) {
        viewModelScope.launch {
            customizationRepository.selectBackground(background)
            // Manually update the UI state
            _uiState.value = _uiState.value.copy(
                customizationState = customizationRepository.customizationState.value
            )
        }
    }

    fun selectClockType(clockType: ClockType) {
        viewModelScope.launch {
            customizationRepository.selectClockType(clockType)
            // Manually update the UI state
            _uiState.value = _uiState.value.copy(
                customizationState = customizationRepository.customizationState.value
            )
        }
    }

    fun selectFont(font: FontFamily) {
        viewModelScope.launch {
            customizationRepository.selectFont(font)
            // Manually update the UI state
            _uiState.value = _uiState.value.copy(
                customizationState = customizationRepository.customizationState.value
            )
        }
    }

    fun selectColor(color: ClockColor) {
        viewModelScope.launch {
            customizationRepository.selectColor(color)
            // Manually update the UI state
            _uiState.value = _uiState.value.copy(
                customizationState = customizationRepository.customizationState.value
            )
        }
    }

    fun toggleSeconds(showSeconds: Boolean) {
        val currentClockType = customizationRepository.customizationState.value.selectedClockType
        if (currentClockType?.isDigital == true) {
            val updatedClockType = when (currentClockType) {
                is ClockType.Digital -> currentClockType.copy(showSeconds = showSeconds)
                is ClockType.DigitalSegments -> currentClockType.copy(showSeconds = showSeconds)
                is ClockType.MorphFlip -> currentClockType.copy(showSeconds = showSeconds)
                else -> currentClockType
            }

            viewModelScope.launch {
                customizationRepository.selectClockType(updatedClockType)
                // Manually update the UI state
                _uiState.value = _uiState.value.copy(
                    customizationState = customizationRepository.customizationState.value
                )
            }
        }
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
    val fontFamilies: List<FontFamily> = emptyList(),
    val clockColors: List<ClockColor> = emptyList(),
    val customizationState: CustomizationState = CustomizationState(),
    val isInCustomizationMode: Boolean = false,
    val isCustomizationApplied: Boolean = false,
    val isLoading: Boolean = false
)