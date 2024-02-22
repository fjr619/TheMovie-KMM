package com.fjr619.themovie_kmm.data.source.remote.datasource

import com.fjr619.themovie_kmm.data.dto.MovieDTO
import com.fjr619.themovie_kmm.data.dto.MovieResponseDTO

interface MovieRemoteDataSource {

    suspend fun getPopularMovieListFromRemote(url: String, page: Int): MovieResponseDTO<List<MovieDTO>>

}