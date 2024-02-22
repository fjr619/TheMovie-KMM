package com.fjr619.themovie_kmm.base.presentation.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class MainDispatcher {
    actual val dispatcher: CoroutineDispatcher = Dispatchers.Main
}