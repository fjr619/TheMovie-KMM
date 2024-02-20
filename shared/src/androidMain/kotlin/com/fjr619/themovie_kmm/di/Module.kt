package com.fjr619.themovie_kmm.di

import com.fjr619.themovie_kmm.data.factory.ApiService
import com.fjr619.themovie_kmm.data.factory.DriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DriverFactory(get()) }
    single { ApiService() }
}