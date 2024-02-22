package com.fjr619.themovie_kmm.di

import com.fjr619.themovie_kmm.MovieDatabase
import com.fjr619.themovie_kmm.data.dto.FailedDto
import com.fjr619.themovie_kmm.data.factory.DriverFactory
import com.fjr619.themovie_kmm.data.repository.MovieListRepositoryImpl
import com.fjr619.themovie_kmm.data.source.local.datasource.MovieLocalDataSource
import com.fjr619.themovie_kmm.data.source.local.datasourceimpl.MovieLocalDataSourceImpl
import com.fjr619.themovie_kmm.data.source.remote.datasource.MovieRemoteDataSource
import com.fjr619.themovie_kmm.data.source.remote.datasourceimpl.MovieListRemoteDataSourceImpl
import com.fjr619.themovie_kmm.data.util.APIConstants
import com.fjr619.themovie_kmm.domain.entity.RequestException
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import com.fjr619.themovie_kmm.domain.usecases.MovieListUseCase
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        loggerModule,
        platformModule(),
        datasourceModule,
        datastoreModuleByPlatform(),
        networkModule,
        useCaseModule,
    )
}

// called by iOS etc
fun initKoin() = initKoin {}

val loggerModule = module {
    single {
        KotlinLogging.logger {}
    }
}

val datasourceModule = module {
    single { MovieDatabase.invoke((get() as DriverFactory).createDriver()) }
    single<MovieRemoteDataSource> { MovieListRemoteDataSourceImpl(get()) }
    single<MovieLocalDataSource> { MovieLocalDataSourceImpl(get()) }
    single<MovieListRepository> { MovieListRepositoryImpl(get(), get(), get(), get()) }
}

val useCaseModule = module {
    factory { MovieListUseCase(get(), get()) }
}

val networkModule = module {
    single {
        HttpClient(get()) {
            expectSuccess = true
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }, contentType = ContentType.Application.Json
                )
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, request ->
                    when (exception) {
                        is ResponseException -> {
                            val dto = exception.response.body<FailedDto>()
                            throw RequestException(
                                statusCode = dto.statusCode,
                                message = dto.statusMessage
                            )
                        }
                    }
                }
            }

            defaultRequest {
                url(APIConstants.BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
    }
}


