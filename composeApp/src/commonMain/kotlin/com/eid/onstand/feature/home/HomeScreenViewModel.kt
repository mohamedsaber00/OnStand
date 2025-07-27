package com.eid.onstand.feature.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eid.onstand.core.data.SettingsRepository
import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class HomeScreenViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _currentTime = MutableStateFlow(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )
    val currentTime: StateFlow<kotlinx.datetime.LocalDateTime> = _currentTime.asStateFlow()

    init {
        loadSettings()
        startTimeUpdates()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                val background = BackgroundRegistry.getByTypeId(settings.backgroundId)
                    ?: BackgroundRegistry.getAll().firstOrNull()

                val clock = ClockRegistry.getByTypeId(settings.clockId)
                    ?: ClockRegistry.getAll().firstOrNull()

                _uiState.value = _uiState.value.copy(
                    selectedBackground = background,
                    selectedClock = clock,
                    selectedFont = settings.fontFamily,
                    selectedColor = settings.textColor,
                    isLoading = false
                )
            }
        }
    }

    private fun startTimeUpdates() {
        viewModelScope.launch {
            while (true) {
                _currentTime.value =
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                kotlinx.coroutines.delay(1000)
            }
        }
    }
}

data class HomeScreenUiState(
    val selectedBackground: BackgroundEffect? = null,
    val selectedClock: ClockWidget? = null,
    val selectedFont: FontFamily = FontFamily.ROBOTO,
    val selectedColor: Color = Color.White,
    val isLoading: Boolean = true
)