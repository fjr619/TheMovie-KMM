package com.fjr619.themovie_kmm.presentation.feature.movielist

import com.fjr619.themovie_kmm.data.util.APIConstants
import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.domain.usecases.MovieListUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.oshai.kotlinlogging.KLogger
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel constructor(
    private val movieListUseCase: MovieListUseCase,
    private val logger: KLogger,
): ViewModel() {

    private val moviesMutable =
        MutableStateFlow<MovieListState>(MovieListState.Uninitialized)
    val movies = moviesMutable.asStateFlow()

    private val selectedMovieMutable = MutableStateFlow(Movie())
    val selectedMovie = selectedMovieMutable.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        moviesMutable.value = MovieListState.Loading
        viewModelScope.launch {
            try {
                movieListUseCase.getMovieList(APIConstants.POPULAR_MOVIES, 20).collect{ movies ->
                    moviesMutable.value = MovieListState.Success(movies)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                moviesMutable.value = MovieListState.Error(e.message.orEmpty())
            }
        }
    }

    fun selectMovie(movie: Movie) {
        selectedMovieMutable.value = movie
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }


}