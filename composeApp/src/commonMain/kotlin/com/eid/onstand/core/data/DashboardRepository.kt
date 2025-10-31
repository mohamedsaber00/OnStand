package com.eid.onstand.core.data

import com.eid.onstand.core.models.DashboardTile
import com.eid.onstand.core.models.TileType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository for managing dashboard tiles.
 * In a real app, this would persist to DataStore or a database.
 */
class DashboardRepository {

    private val _tiles = MutableStateFlow(getDefaultTiles())
    val tiles: Flow<List<DashboardTile>> = _tiles.asStateFlow()

    /**
     * Updates the order of tiles after drag and drop.
     */
    fun updateTileOrder(tiles: List<DashboardTile>) {
        // Update positions based on new order
        val updatedTiles = tiles.mapIndexed { index, tile ->
            tile.copy(position = index)
        }
        _tiles.value = updatedTiles
    }

    /**
     * Gets the current tiles.
     */
    fun getTiles(): List<DashboardTile> = _tiles.value

    /**
     * Resets tiles to default configuration.
     */
    suspend fun resetToDefault() {
        _tiles.value = getDefaultTiles()
    }

    private fun getDefaultTiles(): List<DashboardTile> {
        return listOf(
            DashboardTile(
                id = "clock",
                title = "Clock",
                type = TileType.CLOCK,
                position = 0
            ),
            DashboardTile(
                id = "weather",
                title = "Weather",
                type = TileType.WEATHER,
                position = 1
            ),
            DashboardTile(
                id = "todo",
                title = "Todo List",
                type = TileType.TODO_LIST,
                position = 2
            ),
            DashboardTile(
                id = "notes",
                title = "Notes",
                type = TileType.NOTES,
                position = 3
            )
        )
    }
}
