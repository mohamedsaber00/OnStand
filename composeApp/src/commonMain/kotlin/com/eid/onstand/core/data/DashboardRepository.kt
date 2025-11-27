package com.eid.onstand.core.data

import com.eid.onstand.core.models.DashboardConfig
import com.eid.onstand.core.models.DashboardLayout
import com.eid.onstand.core.models.DashboardTile
import com.eid.onstand.core.models.TileType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Repository for managing dashboard configuration and tiles.
 * Persists settings to DataStore via DashboardDataSource.
 */
class DashboardRepository(
    private val dataSource: DashboardDataSource
) {
    private val _localConfig = MutableStateFlow<DashboardConfig?>(null)

    /**
     * Flow of the current dashboard configuration.
     */
    val config: Flow<DashboardConfig> = dataSource.getDashboardPreferences()
        .map { it.toDashboardConfig() }
        .combine(_localConfig.asStateFlow()) { persisted, local ->
            local ?: persisted
        }

    /**
     * Flow of tiles based on current configuration.
     */
    val tiles: Flow<List<DashboardTile>> = config.map { config ->
        buildTilesFromConfig(config)
    }

    /**
     * Gets the current layout.
     */
    val layout: Flow<DashboardLayout> = config.map { it.layout }

    /**
     * Updates the selected layout.
     */
    suspend fun updateLayout(layout: DashboardLayout) {
        val currentConfig = config.first()
        val updatedConfig = currentConfig.copy(layout = layout)
        _localConfig.value = updatedConfig
    }

    /**
     * Updates the order of tiles after drag and drop.
     */
    suspend fun updateTileOrder(tiles: List<DashboardTile>) {
        val currentConfig = config.first()
        val updatedConfig = currentConfig.copy(
            tileOrder = tiles.map { it.id }
        )
        _localConfig.value = updatedConfig
        saveConfig(updatedConfig)
    }

    /**
     * Saves the current configuration to persistent storage.
     */
    suspend fun saveConfig(config: DashboardConfig? = null) {
        val configToSave = config ?: _localConfig.value ?: this.config.first()
        val preferences = DashboardPreferences.fromDashboardConfig(configToSave)
        dataSource.saveDashboardPreferences(preferences)
        _localConfig.value = null
    }

    /**
     * Discards local changes and reverts to persisted state.
     */
    fun discardChanges() {
        _localConfig.value = null
    }

    /**
     * Resets to default configuration.
     */
    suspend fun resetToDefault() {
        dataSource.clearDashboardPreferences()
        _localConfig.value = null
    }

    /**
     * Gets available tile types that can be shown.
     */
    fun getAvailableTileTypes(): List<TileType> = TileType.entries

    /**
     * Updates which tiles are visible.
     */
    suspend fun updateVisibleTiles(visibleTileIds: Set<String>) {
        val currentConfig = config.first()
        val updatedConfig = currentConfig.copy(visibleTiles = visibleTileIds)
        _localConfig.value = updatedConfig
    }

    private fun buildTilesFromConfig(config: DashboardConfig): List<DashboardTile> {
        val allTiles = getDefaultTiles()
        val tileMap = allTiles.associateBy { it.id }

        // Get tiles in order, filtering by visibility and max tiles for layout
        val orderedTiles = config.tileOrder
            .filter { it in config.visibleTiles }
            .mapNotNull { tileMap[it] }
            .take(config.layout.maxTiles)
            .mapIndexed { index, tile -> tile.copy(position = index) }

        // If we don't have enough tiles, add remaining ones
        if (orderedTiles.size < config.layout.maxTiles) {
            val existingIds = orderedTiles.map { it.id }.toSet()
            val additionalTiles = allTiles
                .filter { it.id !in existingIds && it.id in config.visibleTiles }
                .take(config.layout.maxTiles - orderedTiles.size)
                .mapIndexed { index, tile ->
                    tile.copy(position = orderedTiles.size + index)
                }
            return orderedTiles + additionalTiles
        }

        return orderedTiles
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
