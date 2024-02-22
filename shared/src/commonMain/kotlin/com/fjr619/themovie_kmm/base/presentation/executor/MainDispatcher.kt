package com.fjr619.themovie_kmm.base.presentation.executor

import kotlinx.coroutines.CoroutineDispatcher

expect class MainDispatcher() {
    val dispatcher: CoroutineDispatcher
}