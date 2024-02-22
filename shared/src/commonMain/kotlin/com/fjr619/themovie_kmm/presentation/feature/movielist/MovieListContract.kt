package com.fjr619.themovie_kmm.presentation.feature.movielist

import com.fjr619.themovie_kmm.base.presentation.ui.BasicUiState
import com.fjr619.themovie_kmm.base.presentation.ui.UiEvent
import com.fjr619.themovie_kmm.base.presentation.ui.UiState
import com.fjr619.themovie_kmm.domain.entity.Movie

interface MovieListContract {
    sealed interface Event : UiEvent {
        data class GetMovies(val page: Int) : Event
    }

    data class State(
        val movies: BasicUiState<List<Movie>>
    ) : UiState
}