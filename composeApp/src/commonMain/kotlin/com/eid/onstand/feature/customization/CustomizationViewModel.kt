package com.eid.onstand.feature.customization

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
class CustomizationViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomizationUiState())
    val uiState: StateFlow<CustomizationUiState> = _uiState.asStateFlow()

    private val _currentTime = MutableStateFlow(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )
    val currentTime: StateFlow<kotlinx.datetime.LocalDateTime> = _currentTime.asStateFlow()

    init {
        loadInitialSettings()
        startTimeUpdates()
    }

    private fun loadInitialSettings() {
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
                    backgrounds = BackgroundRegistry.getAll(),
                    clocks = ClockRegistry.getAll(),
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

    fun selectBackground(background: BackgroundEffect) {
        _uiState.value = _uiState.value.copy(selectedBackground = background)
    }

    fun selectClock(clock: ClockWidget) {
        _uiState.value = _uiState.value.copy(selectedClock = clock)
    }

    fun selectFont(font: FontFamily) {
        _uiState.value = _uiState.value.copy(selectedFont = font)
    }

    fun selectColor(color: Color) {
        _uiState.value = _uiState.value.copy(selectedColor = color)
    }

    fun applySettings(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            settingsRepository.saveSettings(
                background = state.selectedBackground,
                clock = state.selectedClock,
                font = state.selectedFont,
                color = state.selectedColor
            )
            onComplete()
        }
    }
}

data class CustomizationUiState(
    val selectedBackground: BackgroundEffect? = null,
    val selectedClock: ClockWidget? = null,
    val selectedFont: FontFamily = FontFamily.ROBOTO,
    val selectedColor: Color = Color.White,
    val backgrounds: List<BackgroundEffect> = emptyList(),
    val clocks: List<ClockWidget> = emptyList(),
    val isLoading: Boolean = true
)