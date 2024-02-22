package com.fjr619.themovie_kmm.data.source.cache

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.oshai.kotlinlogging.KLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppPreferences(
    val dataStore: DataStore<Preferences>
): KoinComponent {

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    fun checkContain() = dataStore.data.map {
        it.contains(stringPreferencesKey(CacheConstants.DATASTORE_MOVIE_LIST_KEY))
    }

    suspend inline fun <reified T> putData(key: String, `object`: T) {
        try {
            val jsonString = Json.encodeToString(`object`)
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = jsonString
            }
        } catch (exception: Exception) {
            throw exception
        }
    }

    inline fun <reified T> getData(key: String, default: T): Flow<T> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val jsonString = preferences[stringPreferencesKey(key)]
            if (jsonString != null) {
                val elements = Json.decodeFromString<T>(jsonString)
                elements
            } else {
                default
            }
        }
    }
}