package com.eid.onstand.feature.preview

import androidx.lifecycle.ViewModel
import com.eid.onstand.core.data.CustomizationRepository
import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.*

class PreviewViewModel(
    private val customizationRepository: CustomizationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PreviewUiState())
    val uiState: StateFlow<PreviewUiState> = _uiState.asStateFlow()

    val customizationState = customizationRepository.customizationState

    init {
        loadCustomizationOptions()
        // For now, just get the initial state without launching a coroutine
        _uiState.value = _uiState.value.copy(
            customizationState = customizationRepository.customizationState.value
        )
    }

    private fun loadCustomizationOptions() {
        _uiState.value = _uiState.value.copy(
            backgroundOptions = customizationRepository.getBackgroundOptions(),
            clockStyles = customizationRepository.getClockStyles(),
            fontColorOptions = customizationRepository.getFontColorOptions(),
            layoutOptions = customizationRepository.getLayoutOptions()
        )

        // Set default selections
        val defaultBackground = customizationRepository.getBackgroundOptions().first()
        val defaultClockStyle = customizationRepository.getClockStyles().first()
        val defaultFontColor = customizationRepository.getFontColorOptions().first()
        val defaultLayout = customizationRepository.getLayoutOptions().first()

        customizationRepository.updateCustomizationState(
            CustomizationState(
                selectedBackground = defaultBackground,
                selectedClockStyle = defaultClockStyle,
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

    fun selectClockStyle(clockStyle: ClockStyle) {
        customizationRepository.selectClockStyle(clockStyle)
        // Manually update the UI state
        _uiState.value = _uiState.value.copy(
            customizationState = customizationRepository.customizationState.value
        )
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
        _uiState.value = _uiState.value.copy(
            isCustomizationApplied = true
        )
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
    val clockStyles: List<ClockStyle> = emptyList(),
    val fontColorOptions: List<FontColorOption> = emptyList(),
    val layoutOptions: List<LayoutOption> = emptyList(),
    val customizationState: CustomizationState = CustomizationState(),
    val isInCustomizationMode: Boolean = false,
    val isCustomizationApplied: Boolean = false,
    val isLoading: Boolean = false
)