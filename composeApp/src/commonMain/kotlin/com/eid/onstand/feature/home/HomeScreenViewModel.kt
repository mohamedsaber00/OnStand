package com.eid.onstand.feature.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eid.onstand.core.data.DashboardRepository
import com.eid.onstand.core.data.SettingsRepository
import com.eid.onstand.core.models.BackgroundEffect
import com.eid.onstand.core.models.Backgrounds
import com.eid.onstand.core.models.DashboardLayout
import com.eid.onstand.core.models.DashboardTile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val settingsRepository: SettingsRepository,
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
        loadDashboard()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                val background = Backgrounds.getByTypeId(settings.backgroundId)
                    ?: Backgrounds.default

                _uiState.update {
                    it.copy(
                        selectedBackground = background,
                        selectedColor = settings.textColor,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            dashboardRepository.layout.collect { layout ->
                _uiState.update { it.copy(layout = layout) }
            }
        }

        viewModelScope.launch {
            dashboardRepository.tiles.collect { tiles ->
                _uiState.update { it.copy(tiles = tiles) }
            }
        }
    }
}

data class HomeScreenUiState(
    val selectedBackground: BackgroundEffect? = null,
    val selectedColor: Color = Color.White,
    val layout: DashboardLayout = DashboardLayout.default,
    val tiles: List<DashboardTile> = emptyList(),
    val isLoading: Boolean = true
)
