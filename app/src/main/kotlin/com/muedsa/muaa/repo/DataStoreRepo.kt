package com.muedsa.muaa.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFS_NAME = "setting"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

class DataStoreRepo @Inject constructor(private val context: Context) {

    suspend fun <T> get(key: Preferences.Key<T>): T? {
        return context.dataStore.data.first()[key]
    }

    suspend fun collectPrefs(collector: FlowCollector<Preferences>) {
        context.dataStore.data.collect(collector)
    }

    suspend fun putString(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit {
            it[key] = value
        }
    }
}