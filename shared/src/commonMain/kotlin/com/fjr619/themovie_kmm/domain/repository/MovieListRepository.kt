package com.fjr619.themovie_kmm.domain.repository

import com.fjr619.themovie_kmm.data.base.Response
import com.fjr619.themovie_kmm.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getPopularMovieList(url: String, page: Int): Flow<Response<List<Movie>>>
}