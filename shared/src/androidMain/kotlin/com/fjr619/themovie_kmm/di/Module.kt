package com.fjr619.themovie_kmm.di

import com.fjr619.themovie_kmm.data.factory.ApiService
import com.fjr619.themovie_kmm.data.factory.DriverFactory
import com.fjr619.themovie_kmm.data.repository.cache.AppPreferences
import com.fjr619.themovie_kmm.data.repository.cache.dataStoreFileName
import com.fjr619.themovie_kmm.data.repository.cache.getDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DriverFactory(get()) }
    single { ApiService() }
}

actual fun datastoreModuleByPlatform() = module {
    single {
        getDataStore {
            androidContext().filesDir?.resolve(dataStoreFileName)?.absolutePath
                ?: throw Exception("Couldn't get Android Datastore context.")
        }
    }

    single { AppPreferences(get()) }
}