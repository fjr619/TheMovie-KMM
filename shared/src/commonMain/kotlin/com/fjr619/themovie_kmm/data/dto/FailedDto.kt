package com.fjr619.themovie_kmm.data.dto

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpCallValidator
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FailedDto(
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message")val statusMessage: String?
)
