package com.fjr619.themovie_kmm.domain.entity

class RequestException(override val message: String?, val statusCode: Int) : Exception(message)
