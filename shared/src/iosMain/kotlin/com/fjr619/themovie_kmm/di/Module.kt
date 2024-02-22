package com.fjr619.themovie_kmm.di

import com.fjr619.themovie_kmm.data.factory.ApiService
import com.fjr619.themovie_kmm.data.factory.DriverFactory
import com.fjr619.themovie_kmm.data.source.cache.AppPreferences
import com.fjr619.themovie_kmm.data.source.cache.dataStoreFileName
import com.fjr619.themovie_kmm.data.source.cache.getDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
actual fun platformModule(): Module = module {
    single { DriverFactory() }
    single { ApiService() }
}

actual fun datastoreModuleByPlatform() = module {
    single {
        getDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$dataStoreFileName"
        }
    }

    single { AppPreferences(get()) }
}