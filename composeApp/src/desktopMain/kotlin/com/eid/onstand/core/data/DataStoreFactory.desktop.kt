package com.eid.onstand.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath

actual fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            System.getProperty("user.home").toPath()
                .resolve(".onstand")
                .resolve("preferences.preferences_pb")
        }
    )
}