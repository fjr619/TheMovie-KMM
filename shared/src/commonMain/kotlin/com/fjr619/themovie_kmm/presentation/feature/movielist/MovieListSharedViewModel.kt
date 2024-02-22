package com.fjr619.themovie_kmm.presentation.feature.movielist

import com.fjr619.themovie_kmm.base.data.Response
import com.fjr619.themovie_kmm.base.presentation.ui.BaseViewModel
import com.fjr619.themovie_kmm.base.presentation.ui.BasicUiState
import com.fjr619.themovie_kmm.base.presentation.ui.UiEffect
import com.fjr619.themovie_kmm.domain.usecases.MovieListUseCase
import org.koin.core.component.inject

open class MovieListSharedViewModel :
    BaseViewModel<MovieListContract.Event, MovieListContract.State, UiEffect>() {

    private val movieListUseCase: MovieListUseCase by inject()
    override fun createInitialState(): MovieListContract.State = MovieListContract.State(movies = BasicUiState.Idle)

    override fun handleEvent(event: MovieListContract.Event) {
        when (event) {
            is MovieListContract.Event.GetMovies -> getPopularMovieList(event.page)
        }
    }

    private fun getPopularMovieList(page: Int) {
        setState { copy(movies = BasicUiState.Loading) }
        collect(movieListUseCase(page)) { movieListResponse ->
            when (movieListResponse) {
                is Response.Error -> setState { copy(movies = BasicUiState.Error()) }
                is Response.Success -> setState {
                    copy(
                        movies =
                        if (movieListResponse.data.isEmpty())
                            BasicUiState.Empty
                        else
                            BasicUiState.Success(movieListResponse.data)
                    )
                }
            }
        }
    }
}