package com.fjr619.themovie_kmm.data.repository

import com.fjr619.themovie_kmm.data.dto.MovieDTO
import com.fjr619.themovie_kmm.data.mappers.mapMovieResponse
import com.fjr619.themovie_kmm.data.mappers.mapToDto
import com.fjr619.themovie_kmm.data.source.cache.AppPreferences
import com.fjr619.themovie_kmm.data.source.cache.CacheConstants
import com.fjr619.themovie_kmm.data.source.local.datasource.MovieLocalDataSource
import com.fjr619.themovie_kmm.data.source.remote.datasource.MovieRemoteDataSource
import com.fjr619.themovie_kmm.data.source.singleSourceOfTruth
import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import io.github.oshai.kotlinlogging.KLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListRepositoryImpl(
    private val movieListRemoteDataSource: MovieRemoteDataSource,
    private val movieListLocalDataSource: MovieLocalDataSource,
    private val preferenceDataStore: AppPreferences,
    private val logger: KLogger,
) : MovieListRepository {

    override fun getPopularMovieList(url: String, page: Int): Flow<List<Movie>> {
        return singleSourceOfTruth(
            logger = logger,
            getLocalData = {
                getPopularMovieListFromLocal()
            },
            getRemoteData = {
                movieListRemoteDataSource.getPopularMovieListFromRemote(url, page).data.map {
                    it.mapMovieResponse()
                }
            },
            saveDataToLocal = { movieList ->
                movieListLocalDataSource.deleteMovieListFromDB()
                movieListLocalDataSource.insertMovieListToDB(movieList)
                saveToCache(movieList)
            }
        )
    }


    private suspend fun getPopularMovieListFromLocal(): List<Movie> {
        var movieList: List<Movie> = emptyList()
        val movieListFlow: Flow<List<MovieDTO>> =
            preferenceDataStore.getData(CacheConstants.DATASTORE_MOVIE_LIST_KEY, emptyList())
        movieList = movieListFlow.first().map { it.mapMovieResponse() }

        if (movieList.isEmpty()) {
            movieList = movieListLocalDataSource.getPopularMovieListFromLocal()
            saveToCache(movieList)
        }
        return movieList
    }

    private suspend fun saveToCache(movieList: List<Movie>) {
        if (movieList.isNotEmpty()) {
            val dto = movieList.map {
                it.mapToDto()
            }
            this.preferenceDataStore.putData(CacheConstants.DATASTORE_MOVIE_LIST_KEY, dto)
        }
    }
}