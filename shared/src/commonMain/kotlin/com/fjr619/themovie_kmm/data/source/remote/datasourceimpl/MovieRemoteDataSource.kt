package com.fjr619.themovie_kmm.data.source.remote.datasourceimpl

import com.fjr619.themovie_kmm.data.dto.MovieDTO
import com.fjr619.themovie_kmm.data.dto.MovieResponseDTO
import com.fjr619.themovie_kmm.data.source.remote.datasource.MovieRemoteDataSource
import com.fjr619.themovie_kmm.data.util.APIConstants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType

class MovieListRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : MovieRemoteDataSource {

    override suspend fun getPopularMovieListFromRemote(url: String, page: Int): MovieResponseDTO<List<MovieDTO>> {
        return httpClient.get(url) {
            parameter("api_key", APIConstants.API_KEY)
            parameter("page", page)
        }.body()
    }
}