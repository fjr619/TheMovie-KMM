package com.fjr619.themovie_kmm.domain.usecases

import com.fjr619.themovie_kmm.base.domain.UseCaseOutFlow
import com.fjr619.themovie_kmm.data.util.APIConstants
import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow

class MovieListUseCase(
    private val movieListRepository: MovieListRepository,
    override val block: suspend (param: Int) -> Flow<List<Movie>> = {
        movieListRepository.getPopularMovieList(APIConstants.POPULAR_MOVIES, it)
    }
) : UseCaseOutFlow<Int, List<Movie>>()