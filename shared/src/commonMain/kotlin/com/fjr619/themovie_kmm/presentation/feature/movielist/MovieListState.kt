package com.fjr619.themovie_kmm.presentation.feature.movielist

import com.fjr619.themovie_kmm.domain.entity.Movie

sealed interface MovieListState {
    data class Success(val data: List<Movie>) : MovieListState
    data class Error(val exceptionMessage: String?) : MovieListState
    object Loading : MovieListState
    object Uninitialized : MovieListState
}