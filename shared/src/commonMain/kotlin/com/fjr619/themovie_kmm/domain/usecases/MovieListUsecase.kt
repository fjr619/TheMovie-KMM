package com.fjr619.themovie_kmm.domain.usecases

import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import io.github.oshai.kotlinlogging.KLogger
import kotlinx.coroutines.flow.Flow

class MovieListUseCase(
    private val movieListRepository: MovieListRepository,
    private val logger: KLogger,
) {
    fun getMovieList(url: String, page: Int): Flow<List<Movie>> {
        return movieListRepository.getPopularMovieList(url, page)
    }
}