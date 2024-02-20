package com.fjr619.themovie_kmm.data.factory

import kotlinx.coroutines.flow.Flow

actual suspend fun <T> Context.putData(key: String, `object`: T) {
}

actual inline fun <reified T> Context.getData(key: String): Flow<T> {
    return emptyFlow()
}

actual inline fun <reified T> Context.getListData(key: String): Flow<List<T>> {
    return emptyFlow()
}