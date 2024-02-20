package com.fjr619.themovie_kmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform