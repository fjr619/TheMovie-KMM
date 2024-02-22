package com.fjr619.themovie_kmm.domain.repository

import com.fjr619.themovie_kmm.base.data.Response
import com.fjr619.themovie_kmm.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    fun getPopularMovieList(url: String, page: Int): Flow<List<Movie>>
}