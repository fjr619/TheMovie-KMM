package com.fjr619.themovie_kmm.data.mappers

import com.fjr619.themovie_kmm.data.dto.MovieDTO
import com.fjr619.themovie_kmm.domain.entity.Movie

fun MovieDTO.mapMovieResponse() = Movie(
    id = this.id,
    title = this.originalTitle,
    overview = this.overview,
    poster_path = buildString {
        append("https://image.tmdb.org/t/p/w600_and_h900_bestv2")
        append(this@mapMovieResponse.posterPath.orEmpty())
    },
    popularity = this.popularity,
)

fun asDomainMovieEntity(
    id: Long,
    title: String,
    overview: String,
    popularity: Long,
    poster_path: String
): Movie = Movie(id, title, overview, poster_path, popularity.toDouble())