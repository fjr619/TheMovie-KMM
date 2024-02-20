package com.fjr619.themovie_kmm.data.repository.remote.datasourceimpl

import com.fjr619.themovie_kmm.data.dto.MovieResponseDTO
import com.fjr619.themovie_kmm.data.factory.ApiService
import com.fjr619.themovie_kmm.data.repository.remote.datasource.MovieRemoteDataSource
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

    override suspend fun getPopularMovieListFromRemote(url: String, page: Int): MovieResponseDTO {
        return httpClient.get(urlString = "${APIConstants.BASE_URL}$url") {
            contentType(ContentType.Application.Json)
            parameter("api_key", APIConstants.API_KEY)
            parameter("page", page)
        }.body()
    }
}