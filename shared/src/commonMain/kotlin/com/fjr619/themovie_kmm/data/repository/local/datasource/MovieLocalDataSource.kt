package com.fjr619.themovie_kmm.data.repository.local.datasource

import com.fjr619.themovie_kmm.domain.entity.Movie

interface MovieListLocalDataSource {
    fun getPopularMovieListFromLocal(): List<Movie>
}