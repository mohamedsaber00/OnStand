package com.eid.onstand

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eid.onstand.core.data.CustomizationRepository
import com.eid.onstand.core.models.CustomizationState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AppViewModel(
    private val customizationRepository: CustomizationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    val customizationState = customizationRepository.customizationState

    init {
        viewModelScope.launch {
            // First load any saved state
            customizationRepository.loadCustomizationState()

            // Then check if we need to set defaults
            val currentState = customizationRepository.customizationState.value
            if (currentState.selectedBackground == null &&
                currentState.selectedClockType == null &&
                currentState.selectedFont == null &&
                currentState.selectedColor == null
            ) {

                val defaultBackgroundType =
                    customizationRepository.getBackgroundTypes().firstOrNull()
                val defaultClockType = customizationRepository.getClockTypes().firstOrNull()
                val defaultFont = customizationRepository.getFontFamilies().firstOrNull()
                val defaultColor = customizationRepository.getClockColors().firstOrNull()

                if (defaultBackgroundType != null && defaultClockType != null &&
                    defaultFont != null && defaultColor != null
                ) {
                    customizationRepository.updateCustomizationState(
                        CustomizationState(
                            selectedBackground = defaultBackgroundType,
                            selectedClockType = defaultClockType,
                            selectedFont = defaultFont,
                            selectedColor = defaultColor,
                        )
                    )
                }
            }
        }
    }

    fun showCustomization() {
        _uiState.value = _uiState.value.copy(showCustomization = true)
    }

    fun hideCustomization() {
        _uiState.value = _uiState.value.copy(showCustomization = false)
    }
}

data class AppUiState(
    val showCustomization: Boolean = false,
    val isLoading: Boolean = false
)