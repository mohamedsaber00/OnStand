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
            customizationRepository.initializeWithSavedState()
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