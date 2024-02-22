package com.fjr619.themovie_kmm.di

import com.fjr619.themovie_kmm.MovieDatabase
import com.fjr619.themovie_kmm.data.factory.DriverFactory
import com.fjr619.themovie_kmm.data.repository.MovieListRepositoryImpl
import com.fjr619.themovie_kmm.data.source.local.datasource.MovieLocalDataSource
import com.fjr619.themovie_kmm.data.source.local.datasourceimpl.MovieLocalDataSourceImpl
import com.fjr619.themovie_kmm.data.source.remote.datasource.MovieRemoteDataSource
import com.fjr619.themovie_kmm.data.source.remote.datasourceimpl.MovieListRemoteDataSourceImpl
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import com.fjr619.themovie_kmm.domain.usecases.MovieListUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        platformModule(),
        datastoreModuleByPlatform(),
        sharedModule
    )
}

// called by iOS etc
fun initKoin() = initKoin{}

val sharedModule = module {
    single { MovieDatabase.invoke((get() as DriverFactory).createDriver()) }
    single<MovieRemoteDataSource> { MovieListRemoteDataSourceImpl(get()) }
    single<MovieLocalDataSource> { MovieLocalDataSourceImpl(get()) }
    single<MovieListRepository> { MovieListRepositoryImpl(get(), get(), get()) }
    factory { MovieListUseCase(get()) }
}
