package com.eid.onstand.core.models

import kotlinx.serialization.Serializable

/**
 * Represents the available layout configurations for the dashboard.
 * Each layout defines how tiles are arranged on the screen.
 */
@Serializable
enum class DashboardLayout(
    val id: String,
    val displayName: String,
    val columns: Int,
    val rows: Int,
    val maxTiles: Int
) {
    /**
     * Single tile layout - one large tile taking full space
     */
    SINGLE(
        id = "single",
        displayName = "Single",
        columns = 1,
        rows = 1,
        maxTiles = 1
    ),

    /**
     * Two tiles side by side horizontally
     */
    TWO_HORIZONTAL(
        id = "two_horizontal",
        displayName = "Two Horizontal",
        columns = 2,
        rows = 1,
        maxTiles = 2
    ),

    /**
     * Two tiles stacked vertically
     */
    TWO_VERTICAL(
        id = "two_vertical",
        displayName = "Two Vertical",
        columns = 1,
        rows = 2,
        maxTiles = 2
    ),

    /**
     * Three tiles - one large on left, two small stacked on right
     */
    THREE_LEFT_DOMINANT(
        id = "three_left",
        displayName = "Focus Left",
        columns = 2,
        rows = 2,
        maxTiles = 3
    ),

    /**
     * Three tiles - two small stacked on left, one large on right
     */
    THREE_RIGHT_DOMINANT(
        id = "three_right",
        displayName = "Focus Right",
        columns = 2,
        rows = 2,
        maxTiles = 3
    ),

    /**
     * Classic 2x2 grid layout with 4 equal tiles
     */
    GRID_2X2(
        id = "grid_2x2",
        displayName = "Grid 2×2",
        columns = 2,
        rows = 2,
        maxTiles = 4
    );

    companion object {
        val default: DashboardLayout = GRID_2X2

        val entries: List<DashboardLayout> = values().toList()

        fun fromId(id: String): DashboardLayout? {
            return entries.find { it.id == id }
        }
    }
}

/**
 * Configuration for dashboard state including layout and tile arrangement.
 */
@Serializable
data class DashboardConfig(
    val layout: DashboardLayout = DashboardLayout.default,
    val tileOrder: List<String> = emptyList(),
    val visibleTiles: Set<String> = emptySet()
) {
    companion object {
        val default = DashboardConfig(
            layout = DashboardLayout.default,
            tileOrder = listOf("clock", "weather", "todo", "notes"),
            visibleTiles = setOf("clock", "weather", "todo", "notes")
        )
    }
}
