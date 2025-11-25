package com.eid.onstand.feature.home

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

class HomeScreenViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                val background = Backgrounds.getByTypeId(settings.backgroundId)
                    ?: Backgrounds.default

                _uiState.value = _uiState.value.copy(
                    selectedBackground = background,
                    selectedColor = settings.textColor,
                    isLoading = false
                )
            }
        }
    }
}

data class HomeScreenUiState(
    val selectedBackground: BackgroundEffect? = null,
    val selectedColor: Color = Color.White,
    val isLoading: Boolean = true
)
