package com.fjr619.themovie_kmm.base.domain

import com.fjr619.themovie_kmm.base.data.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
abstract class UseCaseOutFlow<IN, OUT> {
    operator fun invoke(param: IN): Flow<Response<OUT>> {
        return flow {
            try {
                block(param).map { Response.Success(data = it) }
            } catch (ex: Exception) {
                flowOf(Response.Error(exception = ex))
            }
        }
    }
    protected abstract val block: suspend (param: IN) -> Flow<OUT>
}