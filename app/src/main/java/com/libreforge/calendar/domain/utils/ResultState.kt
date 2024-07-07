package com.libreforge.calendar.domain.utils

typealias RequestCallback<T> = (ResultState<T>) -> Unit

sealed class ResultState<T> {
    /**
     * A state that shows there is still an update to come.
     */
    class Loading<T> : ResultState<T>()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : ResultState<T>()

    /**
     * A state that shows an [error] is thrown.
     */
    data class Error<T>(
        val code: Int,
        val message: String
    ) : ResultState<T>()
}