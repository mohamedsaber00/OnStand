package com.eid.onstand.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eid.onstand.core.data.DashboardRepository
import com.eid.onstand.core.models.DashboardLayout
import com.eid.onstand.core.models.DashboardTile
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * UI state for the dashboard customization screen.
 */
data class DashboardUiState(
    val tiles: List<DashboardTile> = emptyList(),
    val layout: DashboardLayout = DashboardLayout.default,
    val isLoading: Boolean = true
)

/**
 * ViewModel for managing dashboard customization - layout selection and tile reordering.
 */
class DashboardViewModel(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.tiles.collect { tiles ->
                _uiState.update {
                    it.copy(
                        tiles = tiles.sortedBy { tile -> tile.position },
                        isLoading = false
                    )
                }
            }
        }

        viewModelScope.launch {
            repository.layout.collect { layout ->
                _uiState.update {
                    it.copy(layout = layout)
                }
            }
        }
    }

    /**
     * Called when user selects a new layout.
     */
    fun selectLayout(layout: DashboardLayout) {
        viewModelScope.launch {
            repository.updateLayout(layout)
        }
    }

    /**
     * Called when tiles are reordered via drag and drop.
     */
    fun onTilesReordered(reorderedTiles: List<DashboardTile>) {
        viewModelScope.launch {
            repository.updateTileOrder(reorderedTiles)
        }
    }

    /**
     * Saves the current configuration and calls onComplete.
     */
    fun applySettings(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.saveConfig()
            onComplete()
        }
    }

    /**
     * Discards changes and reverts to saved state.
     */
    fun discardChanges() {
        repository.discardChanges()
    }

    /**
     * Resets to default configuration.
     */
    fun resetToDefault() {
        viewModelScope.launch {
            repository.resetToDefault()
        }
    }
}
