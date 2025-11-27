package com.eid.onstand.core.data

import com.eid.onstand.core.models.DashboardConfig
import com.eid.onstand.core.models.DashboardLayout
import kotlinx.serialization.Serializable

/**
 * Serializable preferences for dashboard configuration.
 * Used for persisting dashboard state to DataStore.
 */
@Serializable
data class DashboardPreferences(
    val layoutId: String = DashboardLayout.default.id,
    val tileOrder: List<String> = DashboardConfig.default.tileOrder,
    val visibleTileIds: List<String> = DashboardConfig.default.visibleTiles.toList()
) {
    /**
     * Converts preferences to domain model.
     */
    fun toDashboardConfig(): DashboardConfig {
        return DashboardConfig(
            layout = DashboardLayout.fromId(layoutId) ?: DashboardLayout.default,
            tileOrder = tileOrder,
            visibleTiles = visibleTileIds.toSet()
        )
    }

    companion object {
        val default = DashboardPreferences()

        /**
         * Creates preferences from domain model.
         */
        fun fromDashboardConfig(config: DashboardConfig): DashboardPreferences {
            return DashboardPreferences(
                layoutId = config.layout.id,
                tileOrder = config.tileOrder,
                visibleTileIds = config.visibleTiles.toList()
            )
        }
    }
}
