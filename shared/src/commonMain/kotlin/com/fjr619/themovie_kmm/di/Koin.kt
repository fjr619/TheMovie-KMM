package com.fjr619.themovie_kmm.di

import com.fjr619.themovie_kmm.MovieDatabase
import com.fjr619.themovie_kmm.data.factory.DriverFactory
import com.fjr619.themovie_kmm.data.repository.cache.PreferenceDataStore
import com.fjr619.themovie_kmm.data.repository.local.datasource.MovieLocalDataSource
import com.fjr619.themovie_kmm.data.repository.local.datasourceimpl.MovieLocalDataSourceImpl
import com.fjr619.themovie_kmm.data.repository.remote.datasource.MovieRemoteDataSource
import com.fjr619.themovie_kmm.data.repository.remote.datasourceimpl.MovieListRemoteDataSourceImpl
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        platformModule(),
        sharedModule
    )

}

val sharedModule = module {
    single { MovieDatabase.invoke((get() as DriverFactory).createDriver()) }
    single<MovieRemoteDataSource> { MovieListRemoteDataSourceImpl(get()) }
    single<MovieLocalDataSource> { MovieLocalDataSourceImpl(get()) }
    single { PreferenceDataStore(get()) }
}
