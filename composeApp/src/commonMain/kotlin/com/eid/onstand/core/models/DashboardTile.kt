package com.eid.onstand.core.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Represents a dashboard tile that can be displayed in the grid.
 */
data class DashboardTile(
    val id: String,
    val title: String,
    val type: TileType,
    val position: Int = 0
)

/**
 * Types of tiles available in the dashboard.
 */
enum class TileType {
    TODO_LIST,
    WEATHER,
    CLOCK,
    NOTES
}

/**
 * Interface for tile content renderers.
 * Each tile type should implement this to provide its UI.
 */
interface TileRenderer {
    @Composable
    fun Render(
        tile: DashboardTile,
        isDragging: Boolean,
        modifier: Modifier = Modifier
    )
}
