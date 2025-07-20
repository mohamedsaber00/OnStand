package com.eid.onstand.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "onstand_preferences")

private var appContext: Context? = null

fun initDataStore(context: Context) {
    appContext = context.applicationContext
}

actual fun createDataStore(): DataStore<Preferences> {
    return appContext?.dataStore
        ?: throw IllegalStateException("DataStore not initialized. Call initDataStore() first.")
}