package com.eid.onstand.feature.customization

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eid.onstand.core.data.SettingsRepository
import com.eid.onstand.core.models.BackgroundEffect
import com.eid.onstand.core.models.Backgrounds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomizationViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomizationUiState())
    val uiState: StateFlow<CustomizationUiState> = _uiState.asStateFlow()

    init {
        loadInitialSettings()
    }

    private fun loadInitialSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                val background = Backgrounds.getByTypeId(settings.backgroundId)
                    ?: Backgrounds.default

                _uiState.value = _uiState.value.copy(
                    selectedBackground = background,
                    selectedColor = settings.textColor,
                    backgrounds = Backgrounds.all,
                    isLoading = false
                )
            }
        }
    }

    fun selectBackground(background: BackgroundEffect) {
        _uiState.value = _uiState.value.copy(selectedBackground = background)
    }

    fun applySettings(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            settingsRepository.saveSettings(
                background = state.selectedBackground,
                color = state.selectedColor
            )
            onComplete()
        }
    }
}

data class CustomizationUiState(
    val selectedBackground: BackgroundEffect? = null,
    val selectedColor: Color = Color.White,
    val backgrounds: List<BackgroundEffect> = emptyList(),
    val isLoading: Boolean = true
)
