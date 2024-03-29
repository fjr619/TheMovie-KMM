package com.fjr619.themovie_kmm.di

import com.fjr619.themovie_kmm.data.factory.DriverFactory
import com.fjr619.themovie_kmm.data.source.cache.AppPreferences
import com.fjr619.themovie_kmm.data.source.cache.dataStoreFileName
import com.fjr619.themovie_kmm.data.source.cache.getDataStore
import com.fjr619.themovie_kmm.presentation.feature.movielist.MovieListViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DriverFactory(get()) }
    single { OkHttp.create() }
    viewModel { MovieListViewModel(get(), get()) }
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