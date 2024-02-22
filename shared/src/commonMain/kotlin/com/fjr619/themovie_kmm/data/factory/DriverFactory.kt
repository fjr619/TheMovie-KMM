package com.fjr619.themovie_kmm.data.factory

import app.cash.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

//expect class ApiService() {
//    fun build(): HttpClient
//}