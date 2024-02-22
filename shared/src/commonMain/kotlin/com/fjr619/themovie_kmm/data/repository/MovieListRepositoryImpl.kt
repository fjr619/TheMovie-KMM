package com.fjr619.themovie_kmm.data.repository

import com.fjr619.themovie_kmm.data.base.Response
import com.fjr619.themovie_kmm.data.source.cache.AppPreferences
import com.fjr619.themovie_kmm.data.source.cache.CacheConstants
import com.fjr619.themovie_kmm.data.source.local.datasource.MovieLocalDataSource
import com.fjr619.themovie_kmm.data.source.remote.datasource.MovieRemoteDataSource
import com.fjr619.themovie_kmm.data.source.singleSourceOfTruth
import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow

class MovieListRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val appPreferences: AppPreferences,
) : MovieListRepository {
    override suspend fun getPopularMovieList(url: String, page: Int): Flow<Response<List<Movie>>> {
        return singleSourceOfTruth(
            getLocalData = {
                getPopularMovieListFromLocal()
            },
            getRemoteData = {
                     movieRemoteDataSource.getPopularMovieListFromRemote(url, page).movieDataList
            },
            saveDataToLocal = { movieList ->
                movieLocalDataSource.deleteMovieListFromDB()
                movieLocalDataSource.insertMovieListToDB(movieList)
                appPreferences.putData(CacheConstants.DATASTORE_MOVIE_LIST_KEY, movieList)
            }
        )
    }

    private suspend fun getPopularMovieListFromLocal(): List<Movie> {
        var movieList = emptyList<Movie>()
        val movieListFlow: Flow<List<Movie>> =
            appPreferences.getData(CacheConstants.DATASTORE_MOVIE_LIST_KEY, emptyList())
        movieListFlow.collect {
            movieList = it
        }
        if (movieList.isEmpty()) {
            movieList = movieLocalDataSource.getPopularMovieListFromLocal()
            if (movieList.isNotEmpty()) {
                appPreferences.putData(CacheConstants.DATASTORE_MOVIE_LIST_KEY, movieList)
            }
        }
        return movieList
    }
}