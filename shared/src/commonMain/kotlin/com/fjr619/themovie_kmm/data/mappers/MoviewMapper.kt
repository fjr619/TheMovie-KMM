package com.fjr619.themovie_kmm.data.mappers

import com.fjr619.themovie_kmm.domain.entity.Movie

fun asDomainMovieEntity(
    id: Long,
    title: String,
    overview: String,
    popularity: Long,
    poster_path: String
): Movie = Movie(id, title, overview, popularity, poster_path)