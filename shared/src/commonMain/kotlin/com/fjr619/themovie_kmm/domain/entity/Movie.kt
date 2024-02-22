package com.fjr619.themovie_kmm.domain.entity

data class Movie(
    val id: Long = 0,
    val title: String = "",
    val overview: String = "",
    val poster_path: String = "",
    val popularity: Double =  0.0,
)