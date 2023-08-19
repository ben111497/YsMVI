package com.ys.ysmvi.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val FILE_NAME = "MY_APP"
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

class DataStore(val context: Context) {
    private val dataStore = context.dataStore

    suspend fun <T> getPDS(key: String, defaultValue: T): Flow<T> {
        Log.e("YsDataStore", "getPDS(key: $key)")
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }.map { preferences ->
                val pKey = toPreferencesKey(key, defaultValue)
                if (pKey == null) {
                    Log.e("YsDataStore", "$defaultValue's type does not exist in PreferencesKey")
                    defaultValue
                } else
                    preferences[pKey] ?: defaultValue
            }
    }

    suspend fun <T> setPDS(key: String, value: T) {
        Log.e("YsDataStore", "setPDS(key: $key, value: $value)")
        val pKey = toPreferencesKey(key, value) ?: run {
            Log.e("YsDataStore", "$value's type does not exist in PreferencesKey")
            return
        }
        dataStore.edit { preferences -> preferences[pKey] = value }
    }

    suspend fun <T> removePre(key: Preferences.Key<T>) {
        Log.e("YsDataStore", "removePre(key: $key)")
        dataStore.edit { preferences -> preferences.remove(key) }
    }

    suspend fun clearAllPre() {
        Log.e("YsDataStore", "clearAllPre")
        dataStore.edit { preferences -> preferences.clear() }
    }

    private fun <T> toPreferencesKey(name: String, value: T): Preferences.Key<T>? {
        return when (value) {
            is Boolean -> booleanPreferencesKey(name) as Preferences.Key<T>
            is String -> stringPreferencesKey(name) as Preferences.Key<T>
            is Int -> intPreferencesKey(name) as Preferences.Key<T>
            is Long -> longPreferencesKey(name) as Preferences.Key<T>
            is Float -> floatPreferencesKey(name) as Preferences.Key<T>
            is Double -> doublePreferencesKey(name) as Preferences.Key<T>
            else -> null
        }
    }
}