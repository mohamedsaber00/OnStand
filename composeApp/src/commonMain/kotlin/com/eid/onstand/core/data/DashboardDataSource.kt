package com.eid.onstand.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Data source for persisting dashboard preferences to DataStore.
 */
class DashboardDataSource(
    private val dataStore: DataStore<Preferences>
) {
    private val dashboardKey = stringPreferencesKey("dashboard_preferences")

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    /**
     * Retrieves dashboard preferences as a Flow.
     * Returns default preferences if none are saved or if deserialization fails.
     */
    fun getDashboardPreferences(): Flow<DashboardPreferences> {
        return dataStore.data.map { preferences ->
            preferences[dashboardKey]?.let { jsonString ->
                try {
                    json.decodeFromString<DashboardPreferences>(jsonString)
                } catch (e: Exception) {
                    DashboardPreferences.default
                }
            } ?: DashboardPreferences.default
        }
    }

    /**
     * Saves dashboard preferences to DataStore.
     */
    suspend fun saveDashboardPreferences(preferences: DashboardPreferences) {
        dataStore.edit { prefs ->
            val jsonString = json.encodeToString(preferences)
            prefs[dashboardKey] = jsonString
        }
    }

    /**
     * Clears all dashboard preferences, resetting to defaults.
     */
    suspend fun clearDashboardPreferences() {
        dataStore.edit { preferences ->
            preferences.remove(dashboardKey)
        }
    }
}
