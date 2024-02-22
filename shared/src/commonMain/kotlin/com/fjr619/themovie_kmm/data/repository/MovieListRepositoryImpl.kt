package com.fjr619.themovie_kmm.data.repository

import com.fjr619.themovie_kmm.data.mappers.mapMovieResponse
import com.fjr619.themovie_kmm.data.source.cache.AppPreferences
import com.fjr619.themovie_kmm.data.source.cache.CacheConstants
import com.fjr619.themovie_kmm.data.source.local.datasource.MovieLocalDataSource
import com.fjr619.themovie_kmm.data.source.remote.datasource.MovieRemoteDataSource
import com.fjr619.themovie_kmm.data.source.singleSourceOfTruth
import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow

class MovieListRepositoryImpl(
    private val movieListRemoteDataSource: MovieRemoteDataSource,
    private val movieListLocalDataSource: MovieLocalDataSource,
    private val preferenceDataStore: AppPreferences,
) : MovieListRepository {

    override fun getPopularMovieList(url: String, page: Int): Flow<List<Movie>> =
        singleSourceOfTruth(
            getLocalData = { getPopularMovieListFromLocal() },
            getRemoteData = {
                movieListRemoteDataSource.getPopularMovieListFromRemote(url, page).data.map {
                    it.mapMovieResponse()
                }
            },
            saveDataToLocal = { movieList ->
                movieListLocalDataSource.deleteMovieListFromDB()
                movieListLocalDataSource.insertMovieListToDB(movieList)
                preferenceDataStore.putData(CacheConstants.DATASTORE_MOVIE_LIST_KEY, movieList)
            }
        )

    private suspend fun getPopularMovieListFromLocal(): List<Movie> {
        var movieList: List<Movie> = emptyList()
        val movieListFlow: Flow<List<Movie>> =
            preferenceDataStore.getData(CacheConstants.DATASTORE_MOVIE_LIST_KEY, emptyList())
        movieListFlow.collect {
            movieList = it
        }
        if (movieList.isEmpty()) {
            movieList = movieListLocalDataSource.getPopularMovieListFromLocal()
            if (movieList.isNotEmpty()) {
                this.preferenceDataStore.putData(CacheConstants.DATASTORE_MOVIE_LIST_KEY, movieList)
            }
        }
        return movieList
    }
}