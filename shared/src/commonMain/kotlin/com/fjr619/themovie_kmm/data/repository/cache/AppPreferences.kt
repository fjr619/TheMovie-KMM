package com.fjr619.themovie_kmm.data.repository.cache

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.koin.core.component.KoinComponent

@Serializable
data class Project(val name: String, val language: String)

class AppPreferences(
    val dataStore: DataStore<Preferences>
): KoinComponent {
    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend inline fun <reified T> putData(key: String, `object`: T) {
        try {
            val jsonString = Json.encodeToString(`object`)
//                GsonBuilder().create().toJson(`object`)
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = jsonString
            }
        } catch (exception: Exception) {
//            Log.e("DataStore", "Error writing preferences.", exception)
        }
    }

    inline fun <reified T> getData(key: String): Flow<T> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
//                Log.e("DataStore", "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val jsonString = preferences[stringPreferencesKey(key)]
//            val elements = GsonBuilder().create().fromJson(jsonString, T::class.java)
            val elements = Json.decodeFromString<T>(jsonString!!)
            elements
        }
    }
}