package com.fjr619.themovie_kmm.data.repository.local.datasourceimpl

import com.fjr619.themovie_kmm.MovieDatabase
import com.fjr619.themovie_kmm.data.mappers.asDomainMovieEntity
import com.fjr619.themovie_kmm.data.repository.local.datasource.MovieListLocalDataSource
import com.fjr619.themovie_kmm.domain.entity.Movie

class MovieLocalDataSourceImpl(
    private val movieDatabase: MovieDatabase
): MovieListLocalDataSource {
    private val queries = movieDatabase.movieDatabaseQueries
    override fun getPopularMovieListFromLocal(): List<Movie> {
        return queries.getMovielist(mapper = ::asDomainMovieEntity).executeAsList()
    }
}