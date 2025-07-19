package com.eid.onstand.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.eid.onstand.core.models.SerializableCustomizationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CustomizationDataSource(
    private val dataStore: DataStore<Preferences>
) {
    private val customizationKey = stringPreferencesKey("customization_state")

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    fun getCustomizationState(): Flow<SerializableCustomizationState?> {
        return dataStore.data.map { preferences ->
            preferences[customizationKey]?.let { jsonString ->
                try {
                    json.decodeFromString<SerializableCustomizationState>(jsonString)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    suspend fun saveCustomizationState(state: SerializableCustomizationState) {
        dataStore.edit { preferences ->
            val jsonString = json.encodeToString(state)
            preferences[customizationKey] = jsonString
        }
    }

    suspend fun clearCustomizationState() {
        dataStore.edit { preferences ->
            preferences.remove(customizationKey)
        }
    }
}