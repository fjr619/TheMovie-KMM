package com.fjr619.themovie_kmm.base.presentation.executor

/**
 * In Coroutines, a job is a cancellable thing with a life-cycle that culminates in its completion.
 * Coroutine job is created with launch coroutine builder. It runs a specified block of code and completes on completion of this block.
 *
 * We will create an interface to cancel the Coroutines job when needed.
 * Create an interface IExecutorScope under the same package of commonMainand the below code
 */
interface IExecutorScope {
    fun cancel()
}