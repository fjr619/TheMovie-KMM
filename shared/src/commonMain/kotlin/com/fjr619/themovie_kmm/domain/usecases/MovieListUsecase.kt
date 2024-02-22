package com.fjr619.themovie_kmm.domain.usecases

import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow

class MovieListUseCase(private val movieListRepository: MovieListRepository) {
    fun getMovieList(url: String, page: Int): Flow<List<Movie>> {
        return movieListRepository.getPopularMovieList(url, page)
    }
}