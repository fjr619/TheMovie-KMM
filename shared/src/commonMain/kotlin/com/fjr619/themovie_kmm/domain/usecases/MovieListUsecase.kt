package com.fjr619.themovie_kmm.domain.usecases

import com.fjr619.themovie_kmm.data.base.Response
import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow

class MovieListUseCase(private val movieListRepository: MovieListRepository) {
    suspend fun getPopularMovieList(url: String, page: Int): Flow<Response<List<Movie>>> {
        return movieListRepository.getPopularMovieList(url, page)
    }
}