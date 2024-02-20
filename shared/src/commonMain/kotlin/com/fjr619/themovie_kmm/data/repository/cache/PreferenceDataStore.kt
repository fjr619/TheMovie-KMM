package com.fjr619.themovie_kmm.data.repository.cache

import com.fjr619.themovie_kmm.data.factory.Context
import com.fjr619.themovie_kmm.data.factory.getData
import com.fjr619.themovie_kmm.data.factory.getListData
import com.fjr619.themovie_kmm.data.factory.putData
import kotlinx.coroutines.flow.Flow

class PreferenceDataStore (val context: Context) {

    suspend fun <T> put(key: String, `object`: T) {
        context.putData(key, `object`)
    }

    inline fun <reified T> getData(key: String): Flow<T> =
        context.getData(key)

    inline fun <reified T> getListData(key: String): Flow<List<T>> =
        context.getListData(key)

}