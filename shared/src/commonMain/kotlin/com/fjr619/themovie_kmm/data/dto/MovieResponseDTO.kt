package com.fjr619.themovie_kmm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDTO<T> (
    @SerialName("results")
    val data: T
)