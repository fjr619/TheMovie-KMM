package com.fjr619.themovie_kmm.data.repository.remote.datasource

import com.fjr619.themovie_kmm.data.dto.MovieResponseDTO

interface MovieRemoteDataSource {

    suspend fun getPopularMovieListFromRemote(url: String, page: Int): MovieResponseDTO

}