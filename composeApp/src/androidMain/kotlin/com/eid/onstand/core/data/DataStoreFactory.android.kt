package com.eid.onstand.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath
import java.io.File

actual fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            // Use app's internal cache directory
            val cacheDir = File(System.getProperty("java.io.tmpdir") ?: "/tmp")
            cacheDir.resolve("onstand_preferences.preferences_pb")
                .absolutePath
                .toPath()
        }
    )
}