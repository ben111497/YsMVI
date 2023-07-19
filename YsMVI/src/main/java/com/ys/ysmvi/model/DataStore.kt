package com.ys.ysmvi.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val FILE_NAME = "MY_APP"
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

class DataStore(val context: Context) {
    private val dataStore = context.dataStore

    suspend fun <T> getPDS(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        Log.e("YsDataStore", "getPDS(key: $key)")
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }.map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    suspend fun <T> setPDS(key: Preferences.Key<T>, value: T) {
        Log.e("YsDataStore", "setPDS(key: $key, value: $value)")
        dataStore.edit { preferences -> preferences[key] = value }
    }

    suspend fun <T> removePre(key: Preferences.Key<T>) {
        Log.e("YsDataStore", "removePre(key: $key)")
        dataStore.edit { preferences -> preferences.remove(key) }
    }

    suspend fun clearAllPre() {
        Log.e("YsDataStore", "clearAllPre")
        dataStore.edit { preferences -> preferences.clear() }
    }
}