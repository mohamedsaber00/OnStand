package com.eid.onstand.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eid.onstand.core.data.DashboardRepository
import com.eid.onstand.core.models.DashboardTile
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * UI state for the dashboard screen.
 */
data class DashboardUiState(
    val tiles: List<DashboardTile> = emptyList(),
    val isLoading: Boolean = true
)

/**
 * ViewModel for managing dashboard state and tile reordering.
 */
class DashboardViewModel(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadTiles()
    }

    private fun loadTiles() {
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
     * Resets tiles to their default configuration.
     */
    fun resetToDefault() {
        viewModelScope.launch {
            repository.resetToDefault()
        }
    }
}
