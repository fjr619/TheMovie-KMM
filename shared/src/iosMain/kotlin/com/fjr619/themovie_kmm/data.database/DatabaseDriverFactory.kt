package com.fjr619.themovie_kmm.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.fjr619.themovie_kmm.MovieDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MovieDatabase.Schema, "MovieDatabase.db")
    }
}