package com.fjr619.themovie_kmm.data.repository.local.datasourceimpl

import com.fjr619.themovie_kmm.MovieDatabase
import com.fjr619.themovie_kmm.data.mappers.asDomainMovieEntity
import com.fjr619.themovie_kmm.data.repository.local.datasource.MovieLocalDataSource
import com.fjr619.themovie_kmm.domain.entity.Movie

class MovieLocalDataSourceImpl(
    private val movieDatabase: MovieDatabase
): MovieLocalDataSource {
    private val queries = movieDatabase.movieDatabaseQueries
    override fun insertMovieListToDB(movieList: List<Movie>) {
        for (movieItem in movieList) {
            queries.transaction {
                queries.insertMovielist(
                    id = movieItem.id,
                    title = movieItem.title,
                    overview = movieItem.overview,
                    popularity = movieItem.popularity.toLong(),
                    poster_path = movieItem.poster_path
                )
            }
        }
    }

    override fun getPopularMovieListFromLocal(): List<Movie> {
        return queries.getMovielist(mapper = ::asDomainMovieEntity).executeAsList()
    }
}